package backend.useraccess.repository;

import backend.useraccess.entity.UserAccess;

import java.util.List;

public interface UserAccessRepository {

    UserAccess save(UserAccess userAccess);

    UserAccess findById(Long id);

    List<UserAccess> findAll();

    void delete(UserAccess userAccess);

}
