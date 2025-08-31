package com.ftm.main.filemanager.fileOperations.controller;

import com.ftm.main.filemanager.fileOperations.dto.chunkResponse.ChunkRequest;
import com.ftm.main.filemanager.fileOperations.dto.chunkResponse.ChunkResponse;
import com.ftm.main.filemanager.fileOperations.dto.chunkResponse.ChunkStatusResponse;
import com.ftm.main.filemanager.fileOperations.dto.errorResponse.ErrorResponse;
import com.ftm.main.filemanager.fileOperations.dto.fileResponse.FileRequest;
import com.ftm.main.filemanager.fileOperations.dto.fileResponse.FileResponse;
import com.ftm.main.filemanager.fileOperations.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/file")
public class FileManagementController {

    @Autowired
    private FileService fileService;

    @PostMapping("/initiate-upload")
    public CompletableFuture<ResponseEntity<FileResponse>> initiateUpload(@RequestBody FileRequest fileRequest){
        return CompletableFuture.supplyAsync(()->ResponseEntity.status(201).body(fileService.getPendingChunks(fileRequest)));
    }

    @PostMapping("/markFile")
    public CompletableFuture<ResponseEntity<ChunkStatusResponse>> markChunkCompleted(@RequestBody ChunkRequest chunkRequest){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(200).body(fileService.markChunkCompleted(chunkRequest)));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleErrors(IllegalStateException e){
        return ResponseEntity.status(400).body(ErrorResponse.builder().status("error").message(e.getMessage()).build());
    }

    @PostMapping("/refreshChunk")
    public CompletableFuture<ResponseEntity<ChunkResponse>> refreshChunkUrl(@RequestParam String fileId, @RequestParam Integer chunkId){
        return CompletableFuture.supplyAsync(()->ResponseEntity.ok(fileService.refreshUrl(fileId,chunkId)));
    }
}
