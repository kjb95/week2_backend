package backend.useraccess.controller;

import backend.useraccess.entity.UserAccess;
import backend.useraccess.repository.UserAccessJpaRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@DataJpaTest
public class RepositoryTest {
    @Autowired
    private UserAccessJpaRepository userAccessJpaRepository;

    public UserAccess userAccess() {
        return UserAccess.builder()
                .basicDate("2023.01.01")
                .impCnt(123L)
                .clickCnt(234L)
                .convCnt(345L)
                .sellCost(456L)
                .adspend(567L)
                .build();
    }

    @Test
    public void createUserAccess() throws Exception{
        UserAccess userAccess = userAccess();
        userAccessJpaRepository.save(userAccess);
        System.out.println(userAccess.getId());
    }


}
