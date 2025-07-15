package com.ftm.main.filemanager.fileOperations.dto.chunkResponse;

import com.ftm.main.filemanager.fileOperations.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChunkStatusResponse {
    private String fileId;
    private String chunkId;
    private Status status;
}
