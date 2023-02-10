package backend.useraccess.controller;

import backend.useraccess.UserAccessTestDummy;
import backend.useraccess.dto.CreateUserAccessRequestDto;
import backend.useraccess.dto.UpdateUserAccessRequestDto;
import backend.useraccess.entity.UserAccess;
import backend.useraccess.repository.UserAccessJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserAccessControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    UserAccessJpaRepository userAccessJpaRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        userAccessJpaRepository.deleteAll();
    }

    @Test
    public void 유저_접근_데이터_생성_성공() throws Exception {
        // given
        CreateUserAccessRequestDto request = UserAccessTestDummy.validCreateUserAccessRequestDto();
        // when
        ResultActions resultActions = mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        UserAccess userAccess = userAccessJpaRepository.findAll()
                .get(0);
        // then
        Assertions.assertThat(userAccess.getBasicDate())
                .isEqualTo(request.getBasicDate());
        Assertions.assertThat(userAccess.getImpCnt())
                .isEqualTo(request.getImpCnt());
        Assertions.assertThat(userAccess.getClickCnt())
                .isEqualTo(request.getClickCnt());
        Assertions.assertThat(userAccess.getConvCnt())
                .isEqualTo(request.getConvCnt());
        Assertions.assertThat(userAccess.getSellCost())
                .isEqualTo(request.getSellCost());
        Assertions.assertThat(userAccess.getAdspend())
                .isEqualTo(request.getAdspend());
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 유저_접근_데이터_생성_바인딩_에외() throws Exception {
        // given
        List<CreateUserAccessRequestDto> requests = Arrays.asList(UserAccessTestDummy.createUserAccessRequestDtoNotnullException(), UserAccessTestDummy.createUserAccessRequestDtoPatternException(), UserAccessTestDummy.createUserAccessRequestDtoMinException());
        for (CreateUserAccessRequestDto request : requests) {
            // when
            ResultActions resultActions = mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
            // then
            resultActions.andExpect(status().isBadRequest());
        }
    }

    @Test
    public void 유저_접근_데이터_생성_허용하지_않는_HTTP_메소드_예외() throws Exception {
        // given
        CreateUserAccessRequestDto request = UserAccessTestDummy.validCreateUserAccessRequestDto();
        // when
        ResultActions resultActions = mvc.perform(get("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        // then
        resultActions.andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void 유저_접근_데이터_아이디로_조회_성공() throws Exception {
        // given
        CreateUserAccessRequestDto request = UserAccessTestDummy.validCreateUserAccessRequestDto();
        mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        Long id = userAccessJpaRepository.findAll()
                .get(0)
                .getId();
        // when
        ResultActions resultActions = mvc.perform(get("/api/user-access/" + id));
        // then
        resultActions
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.basicDate").value("2023.01.01"))
                .andExpect(jsonPath("$.impCnt").value(123L))
                .andExpect(jsonPath("$.clickCnt").value(234L))
                .andExpect(jsonPath("$.convCnt").value(345L))
                .andExpect(jsonPath("$.sellCost").value(456L))
                .andExpect(jsonPath("$.adspend").value(567L))
                .andExpect(status().isOk());
    }

    @Test
    public void 유저_접근_데이터_아이디로_조회할때_존재하지_않는_아이디_예외() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(get("/api/user-access/10000"));
        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void 유저_접근_데이터_전체조회_성공() throws Exception {
        // given
        CreateUserAccessRequestDto request = UserAccessTestDummy.validCreateUserAccessRequestDto();
        mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        // when
        ResultActions resultActions = mvc.perform(get("/api/user-access/all"));
        // then
        resultActions.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void 유저_접근_데이터_수정_성공() throws Exception {
        // given
        CreateUserAccessRequestDto crateRequest = UserAccessTestDummy.validCreateUserAccessRequestDto();
        mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crateRequest)));
        Long id = userAccessJpaRepository.findAll()
                .get(0)
                .getId();
        UpdateUserAccessRequestDto updateRequest = UserAccessTestDummy.updateBasicDateUserAccessRequestDto();
        // when
        UserAccess userAccess = userAccessJpaRepository.findAll()
                .get(0);
        Assertions.assertThat(userAccess.getBasicDate())
                .isNotEqualTo(updateRequest.getBasicDate());
        ResultActions resultActions = mvc.perform(put("/api/user-access/" + id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));
        userAccess = userAccessJpaRepository.findAll()
                .get(0);
        // then
        Assertions.assertThat(userAccess.getBasicDate())
                .isEqualTo(updateRequest.getBasicDate());
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 유저_접근_데이터_수정할때_존재하지_않는_아이디_예외() throws Exception {
        // given
        UpdateUserAccessRequestDto updateRequest = UserAccessTestDummy.updateBasicDateUserAccessRequestDto();
        // when
        ResultActions resultActions = mvc.perform(put("/api/user-access/10000").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));
        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void 유저_접근_데이터_삭제_성공() throws Exception {
        // given
        CreateUserAccessRequestDto crateRequest = UserAccessTestDummy.validCreateUserAccessRequestDto();
        mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crateRequest)));
        Long id = userAccessJpaRepository.findAll()
                .get(0)
                .getId();
        // when
        int dataSize = userAccessJpaRepository.findAll()
                .size();
        Assertions.assertThat(dataSize)
                .isEqualTo(1);
        ResultActions resultActions = mvc.perform(delete("/api/user-access/" + id));
        dataSize = userAccessJpaRepository.findAll()
                .size();
        // then
        Assertions.assertThat(dataSize)
                .isEqualTo(0);
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 유저_접근_데이터_삭제할때_존재하지_않는_아이디_예외() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(delete("/api/user-access/10000"));
        // then
        resultActions.andExpect(status().isNotFound());
    }

}