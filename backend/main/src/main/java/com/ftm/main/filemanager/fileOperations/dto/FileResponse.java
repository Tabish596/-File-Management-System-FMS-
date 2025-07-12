package com.ftm.main.filemanager.fileOperations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FileResponse {
    private String fileId;
    private List<ChunkResponse> chunks;
}
