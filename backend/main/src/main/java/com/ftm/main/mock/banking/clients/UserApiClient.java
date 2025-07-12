package com.ftm.main.mock.banking.clients;

import com.ftm.main.mock.banking.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserApiClient {

    private final WebClient webClient;

    public UserApiClient(WebClient.Builder builder, @Value("${external.api.user-service}") String baseUrl){
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public UserResponse getUserById(int page) {
        return webClient.get()
                .uri("/users?page={page}", page)
                .header("x-api-key","reqres-free-v1")
                .retrieve()
                .bodyToMono(UserResponse.class).block();
    }

}
