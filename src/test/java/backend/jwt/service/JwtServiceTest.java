package backend.jwt.service;

import backend.domain.client.entity.Client;
import backend.domain.client.repository.ClientRepository;
import backend.jwt.dto.AuthenticateClientRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

import javax.transaction.Transactional;

import static backend.domain.client.ClientTestDummy.createAdminAuthenticateRequestDto;
import static backend.domain.client.ClientTestDummy.createAdminClient;

@SpringBootTest
@Transactional
class JwtServiceTest {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        clientRepository.deleteAll();
    }

    @Test
    void authenticateClient_성공() {
        // given
        Client client = createAdminClient();
        clientRepository.save(client);
        AuthenticateClientRequestDto request = createAdminAuthenticateRequestDto();
        // then
        Assertions.assertThatCode(() -> jwtService.authenticateClient(request))
                .doesNotThrowAnyException();
    }

    @Test
    void authenticateClient_존재하지않는_클라이언트_예외() {
        // given
        AuthenticateClientRequestDto request = createAdminAuthenticateRequestDto();
        // then
        Assertions.assertThatThrownBy(() -> jwtService.authenticateClient(request))
                .isInstanceOf(BadCredentialsException.class);
    }
}