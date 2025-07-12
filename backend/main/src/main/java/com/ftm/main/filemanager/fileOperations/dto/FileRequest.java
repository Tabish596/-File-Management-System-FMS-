package com.ftm.main.filemanager.fileOperations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {
    private String fileName;
    private Long fileSize;
    private String mimeType;
    private String ownerId;
    List<ChunkRequest> chunks;
}
