package backend.domain.client.controller;

import backend.domain.client.dto.AuthoritiesResponseDto;
import backend.domain.client.entity.Authority;
import backend.domain.client.entity.Client;
import backend.domain.client.repository.AuthorityRepository;
import backend.domain.client.repository.ClientRepository;
import backend.jwt.service.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.List;

import static backend.domain.client.ClientTestDummy.createAdminClient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class ClientControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private JwtService jwtService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        clientRepository.deleteAll();
        authorityRepository.save(new Authority("ROLE_USER"));
        authorityRepository.save(new Authority("ROLE_MANAGER"));
        authorityRepository.save(new Authority("ROLE_ADMIN"));
    }

    @WithMockUser
    @Test
    public void 유저가_가진_모든_권한_조회_성공() throws Exception {
        // given
        Client client = createAdminClient();
        clientRepository.save(client);
        // when
        String contentAsString = mvc.perform(get("/api/client/admin"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        AuthoritiesResponseDto authoritiesResponseDto = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });
        List<String> responseAuthorities = authoritiesResponseDto.getAuthorities();
        // then
        Assertions.assertThat(responseAuthorities.size())
                .isEqualTo(3);
        Assertions.assertThat(responseAuthorities)
                .contains("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN");
    }

    @WithMockUser
    @Test
    public void 유저가_가진_모든_권한_조회할때_존재하지_않는_아이디_예외() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(get("/api/client/admin"));
        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}