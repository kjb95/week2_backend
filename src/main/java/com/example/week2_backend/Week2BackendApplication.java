package com.example.week2_backend;

import com.example.week2_backend.repository.UserAccessRepository;
import com.example.week2_backend.repository.impl.UserAccessRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week2BackendApplication {
    public static void main(String[] args) {
        UserAccessRepository userAccessRepository = new UserAccessRepositoryImpl();

        SpringApplication.run(Week2BackendApplication.class, args);
    }

}
