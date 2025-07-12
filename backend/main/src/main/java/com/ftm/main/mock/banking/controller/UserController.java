package com.ftm.main.mock.banking.controller;

import com.ftm.main.mock.banking.dto.UserResponse;
import com.ftm.main.mock.banking.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUserByPageNumber(@RequestParam(name="page") int page){
        return ResponseEntity.ok(userService.getUser(page));
    }
}
