package com.example.week2_backend.controller;

import com.example.week2_backend.dto.UserAccessDto;
import com.example.week2_backend.service.UserAccessService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-access")
public class UserAccessController {

    private final UserAccessService userAccessService;

    @GetMapping("/all")
    public List<UserAccessDto> findAllUserAccess() {
        return userAccessService.findAllUserAccess();
    }
}
