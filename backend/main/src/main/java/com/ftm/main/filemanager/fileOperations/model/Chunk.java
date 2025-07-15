package com.ftm.main.filemanager.fileOperations.model;

import com.ftm.main.filemanager.fileOperations.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value="chunk")
public class Chunk {

    @Id
    private String id;
    private String fileId;
    private Integer index;
    private String hash;
    private String size;
    private Status status;
    private Instant generatedAt;
}
