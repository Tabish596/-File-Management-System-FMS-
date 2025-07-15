package com.ftm.main.filemanager.fileOperations.dto.chunkResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChunkResponse {
    private Integer index;
    private String hash;
    private String s3Url;
    private String status;
}
