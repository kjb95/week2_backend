package backend.domain.useraccess.repository;

import backend.domain.useraccess.entity.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessJpaRepository extends JpaRepository<UserAccess, Long> {
}
