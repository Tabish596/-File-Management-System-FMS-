package com.ftm.main.filemanager.fileOperations.service;

import com.ftm.main.filemanager.fileOperations.dto.chunkResponse.ChunkRequest;
import com.ftm.main.filemanager.fileOperations.dto.chunkResponse.ChunkResponse;
import com.ftm.main.filemanager.fileOperations.dto.chunkResponse.ChunkStatusResponse;
import com.ftm.main.filemanager.fileOperations.dto.fileResponse.FileRequest;
import com.ftm.main.filemanager.fileOperations.dto.fileResponse.FileResponse;
import com.ftm.main.filemanager.fileOperations.enums.Status;
import com.ftm.main.filemanager.fileOperations.model.Chunk;
import com.ftm.main.filemanager.fileOperations.model.File;
import com.ftm.main.filemanager.fileOperations.repository.ChunkRepository;
import com.ftm.main.filemanager.fileOperations.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    FileRepository fileRepository;
    ChunkRepository chunkRepository;
    S3PresignedUrlService s3Service;

    @Value("${aws.s3.basePath}")
    private String basePath;

    @Value("${aws.s3.chunk.duration}")
    private Long duration;

    FileService(FileRepository fileRepository,ChunkRepository chunkRepository,S3PresignedUrlService s3Service){
        this.fileRepository=fileRepository;
        this.chunkRepository=chunkRepository;
        this.s3Service=s3Service;
    }

    public FileResponse getPendingChunks(FileRequest fileRequest){
        Optional<File> file = fileRepository.findByOwnerIdAndName(fileRequest.getOwnerId(),fileRequest.getFileName());
        if(file.isEmpty()){
            File newFile = File.builder()
                    .name(fileRequest.getFileName())
                    .size(fileRequest.getFileSize())
                    .mimetype(fileRequest.getMimeType())
                    .ownerId(fileRequest.getOwnerId())
                    .key(basePath+fileRequest.getOwnerId())
                    .status(Status.PENDING)
                    .build();
            newFile = fileRepository.save(newFile);
            String fileId = newFile.getId();

            List<ChunkRequest> chunkRequests = fileRequest.getChunks();
            List<Chunk> chunks = chunkRequests.stream()
                    .map(req ->
                         Chunk.builder()
                            .fileId(fileId)
                            .index(req.getIndex())
                            .hash(req.getHash())
                            .size(req.getSize())
                            .status(Status.PENDING)
                            .generatedAt(Instant.now())
                            .build())
                    .toList();
            chunks = chunkRepository.saveAll(chunks);

            return mapToFileResponses(chunks,newFile.getKey(),fileId);
        }

        File currentFile = file.get();
        Status fileStatus = currentFile.getStatus();
        if(fileStatus == Status.PENDING){
            List<Chunk> pendingChunks = chunkRepository.findByFileIdAndStatus(currentFile.getId(),Status.PENDING);
            return mapToFileResponses(pendingChunks,currentFile.getKey(),currentFile.getId());
        }
        throw new IllegalStateException("File upload already completed or invalid state: " + currentFile.getStatus());
    }

    public ChunkStatusResponse markChunkCompleted(ChunkRequest chunkRequest){
        Optional<Chunk> chunkOptional = chunkRepository.findByFileIdAndIndex(chunkRequest.getFileId(),chunkRequest.getIndex());
        if(chunkOptional.isPresent()){
            Chunk chunk = chunkOptional.get();
            chunk.setStatus(Status.COMPLETED);
            chunk = chunkRepository.save(chunk);
            return ChunkStatusResponse.builder()
                    .status(chunk.getStatus())
                    .chunkId(chunk.getId())
                    .fileId(chunk.getFileId())
                    .build();
        }
        throw new IllegalStateException("Incorrect chunk info provided " + chunkRequest.getHash());
    }

    private FileResponse mapToFileResponses(List<Chunk> chunks, String filePath, String fileId) {
        List<ChunkResponse> chunkResponses =  chunks.stream()
                .map(chunk -> ChunkResponse.builder()
                        .status(chunk.getStatus().toString())
                        .index(chunk.getIndex())
                        .hash(chunk.getHash())
                        .s3Url(s3Service.generatePutUrl(filePath + "/" + chunk.getHash(), Duration.ofMinutes(duration)).toString())
                        .build())
                .toList();
        return FileResponse.builder().fileId(fileId).chunks(chunkResponses).build();
    }

    public ChunkResponse refreshUrl(String fileId, Integer chunkId){
        Optional<Chunk> chunkOptional = chunkRepository.findByFileIdAndIndex(fileId,chunkId);
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if(chunkOptional.isPresent() && fileOptional.isPresent()){
            Chunk chunk = chunkOptional.get();
            File file = fileOptional.get();
            Duration expirationDuration = Duration.ofMinutes(duration);
            if(chunk.getStatus().equals(Status.PENDING) && Instant.now().isAfter(chunk.getGeneratedAt().plus(expirationDuration))){
                chunk.setGeneratedAt(Instant.now());
                chunkRepository.save(chunk);
                return ChunkResponse.builder()
                        .status(chunk.getStatus().toString())
                        .index(chunk.getIndex())
                        .hash(chunk.getHash())
                        .s3Url(s3Service.generatePutUrl(file.getKey() + "/" + chunk.getHash(), Duration.ofMinutes(duration)).toString())
                        .build();
            }
        }
        throw new IllegalStateException("Incorrect chunk info provided fileId "+fileId+" chunkId-"+chunkId);
    }
}
