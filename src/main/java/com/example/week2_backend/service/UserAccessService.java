package com.example.week2_backend.service;

import com.example.week2_backend.dto.UserAccessDto;
import com.example.week2_backend.repository.UserAccessRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccessService {

    private final UserAccessRepository userAccessRepository;

    public List<UserAccessDto> findAllUserAccess() {
        ModelMapper modelMapper = new ModelMapper();
        return userAccessRepository.findAll()
                .stream()
                .map(userAccess -> modelMapper.map(userAccess, UserAccessDto.class))
                .collect(Collectors.toList());
    }
}
