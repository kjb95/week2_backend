package backend.domain.client.service;

import backend.domain.client.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class ClientServiceTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        clientRepository.deleteAll();
    }

    @Test
    void findAuthorities_존재하지않는_클라이언트_예외() {
        // then
        Assertions.assertThatThrownBy(() -> clientService.findAuthorities("admin"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}