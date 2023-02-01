package com.example.week2_backend.repository;

import com.example.week2_backend.entity.UserAccess;
import java.util.List;

public interface UserAccessRepository {

    UserAccess save(UserAccess userAccess);

    UserAccess findById(Long id);

    List<UserAccess> findAll();
}
