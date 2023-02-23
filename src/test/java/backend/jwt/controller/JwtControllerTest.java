package backend.jwt.controller;

import backend.domain.client.entity.Client;
import backend.domain.client.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static backend.domain.client.ClientTestDummy.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JwtControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ClientRepository clientRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        clientRepository.deleteAll();
    }

    @Test
    public void 유효한_사용자면_jwt_토큰_반환_성공() throws Exception {
        // given
        Client client = createAdminClient();
        clientRepository.save(client);
        // when
        ResultActions resultActions = mvc.perform(post("/api/jwt").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAdminAuthenticateRequestDto())));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 유효하지_사용자면_jwt_토큰_반환_실패() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(post("/api/jwt").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAdminAuthenticateRequestDto())));
        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}