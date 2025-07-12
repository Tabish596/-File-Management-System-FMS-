package com.ftm.main.filemanager.fileOperations.controller;

import com.ftm.main.filemanager.fileOperations.dto.ChunkResponse;
import com.ftm.main.filemanager.fileOperations.dto.FileRequest;
import com.ftm.main.filemanager.fileOperations.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/file")
public class FileManagementController {

    @Autowired
    FileService fileService;

    @PostMapping("/initiate-upload")
    public CompletableFuture<List<ChunkResponse>> initiateUpload(@RequestBody FileRequest fileRequest){
        return CompletableFuture.supplyAsync(()->fileService.getPendingChunks(fileRequest));
    }


}
