package com.ftm.main.filemanager.fileOperations.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="folder")
public class Folder {

    @Id
    private String id;
    private String name;
    private boolean isParent;
}
