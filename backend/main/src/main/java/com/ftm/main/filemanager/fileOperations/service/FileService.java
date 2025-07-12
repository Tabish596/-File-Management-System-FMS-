package com.ftm.main.filemanager.fileOperations.service;

import com.ftm.main.filemanager.fileOperations.dto.ChunkRequest;
import com.ftm.main.filemanager.fileOperations.dto.ChunkResponse;
import com.ftm.main.filemanager.fileOperations.dto.FileRequest;
import com.ftm.main.filemanager.fileOperations.enums.Status;
import com.ftm.main.filemanager.fileOperations.model.Chunk;
import com.ftm.main.filemanager.fileOperations.model.File;
import com.ftm.main.filemanager.fileOperations.repository.ChunkRepository;
import com.ftm.main.filemanager.fileOperations.repository.FileRepository;
import com.ftm.main.filemanager.userManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ChunkResponse> getPendingChunks(FileRequest fileRequest){
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
                    .map(req -> Chunk.builder()
                            .fileId(fileId)
                            .index(req.getIndex())
                            .hash(req.getHash())
                            .size(req.getSize())
                            .status(Status.PENDING)
                            .build())
                    .toList();
            chunks = chunkRepository.saveAll(chunks);

            return mapToChunkResponses(chunks,newFile.getKey());
        }

        File currentFile = file.get();
        Status fileStatus = currentFile.getStatus();
        if(fileStatus == Status.PENDING){
            List<Chunk> pendingChunks = chunkRepository.findByFileIdAndStatus(currentFile.getId(),Status.PENDING);
            return mapToChunkResponses(pendingChunks,currentFile.getKey());
        }
        throw new IllegalStateException("File upload already completed or invalid state: " + currentFile.getStatus());
    }

    private List<ChunkResponse> mapToChunkResponses(List<Chunk> chunks, String filePath) {
        return chunks.stream()
                .map(chunk -> ChunkResponse.builder()
                        .status(chunk.getStatus().toString())
                        .index(chunk.getIndex())
                        .hash(chunk.getHash())
                        .s3Url(s3Service.generatePutUrl(filePath + "/" + chunk.getHash(), Duration.ofMinutes(15)).toString())
                        .build())
                .toList();
    }

}
