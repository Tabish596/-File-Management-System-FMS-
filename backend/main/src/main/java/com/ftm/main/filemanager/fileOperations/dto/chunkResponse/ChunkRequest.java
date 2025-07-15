package com.ftm.main.filemanager.fileOperations.dto.chunkResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChunkRequest {
    private String fileId;
    private Integer index;
    private String hash;
    private String size;
}
