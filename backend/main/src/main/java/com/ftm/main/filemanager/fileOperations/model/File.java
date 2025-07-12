package com.ftm.main.filemanager.fileOperations.model;

import com.ftm.main.filemanager.fileOperations.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "file")
@Builder
public class File {

    @Id
    private String id;
    private String name;
    private Long size;
    private String mimetype;
    private String ownerId;
    private String creationTimeStamp;
    private String updateTimeStamp;
    private Status status;
    private String key;
}
