package com.ftm.main.mock.banking.services;

import com.ftm.main.mock.banking.clients.UserApiClient;
import com.ftm.main.mock.banking.dto.UserResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserApiClient userApiClient;

    public UserService(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }

    public UserResponse getUser(int page) {
        return userApiClient.getUserById(page);
    }
}
