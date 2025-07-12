package com.ftm.main.filemanager.userManagement.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="user")
public class User {

    @Id
    private String id;
    private String userId;
    private String name;
    private String email;
    private String role;
}
