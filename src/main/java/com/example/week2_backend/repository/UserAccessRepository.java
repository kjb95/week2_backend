package com.example.week2_backend.repository;

import com.example.week2_backend.entity.UserAccess;

import java.util.List;

public interface UserAccessRepository {
    public UserAccess save(UserAccess userAccess);

    public UserAccess findById(Long id);

    public List<UserAccess> findAll();
}
