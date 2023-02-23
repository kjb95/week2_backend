package backend.domain.client.service;

import backend.domain.client.entity.Client;
import backend.domain.client.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static backend.domain.client.ClientTestDummy.createAdminClient;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class ClientServiceMockTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void findAuthorities_성공() {
        // given
        Client expectedAdminClient = createAdminClient();
        given(clientRepository.findOneWithAuthoritiesByUsername("admin")).willReturn(Optional.ofNullable(expectedAdminClient));
        // when
        List<String> outputs = clientService.findAuthorities("admin")
                .getAuthorities();
        // then
        Assertions.assertThat(outputs)
                .contains("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN");
    }
}