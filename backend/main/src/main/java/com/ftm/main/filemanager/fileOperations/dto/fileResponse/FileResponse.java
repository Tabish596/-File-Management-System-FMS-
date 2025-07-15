package com.ftm.main.filemanager.fileOperations.dto.fileResponse;

import com.ftm.main.filemanager.fileOperations.dto.chunkResponse.ChunkResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class FileResponse {
    private String fileId;
    private List<ChunkResponse> chunks;
}
