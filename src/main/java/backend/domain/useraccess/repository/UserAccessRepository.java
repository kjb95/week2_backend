package backend.domain.useraccess.repository;

import backend.domain.useraccess.entity.UserAccess;

import java.util.List;
import java.util.Optional;

public interface UserAccessRepository {

    UserAccess save(UserAccess userAccess);

    Optional<UserAccess> findById(Long id);

    List<UserAccess> findAll();

    void delete(UserAccess userAccess);

}
