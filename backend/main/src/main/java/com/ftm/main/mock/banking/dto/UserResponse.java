package com.ftm.main.mock.banking.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<UserDetails> data;

}
