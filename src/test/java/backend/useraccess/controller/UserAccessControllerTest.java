package backend.useraccess.controller;

import backend.domain.useraccess.dto.CreateUserAccessRequestDto;
import backend.domain.useraccess.dto.UpdateUserAccessRequestDto;
import backend.domain.useraccess.entity.UserAccess;
import backend.domain.useraccess.repository.UserAccessJpaRepository;
import backend.useraccess.UserAccessTestDummy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.stream.Stream;

import static backend.useraccess.UserAccessTestDummy.validCreateUserAccessRequestDto;
import static backend.useraccess.UserAccessTestUtils.assertUserAccessAndCreateUserAccessRequestDto;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserAccessControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserAccessJpaRepository userAccessJpaRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static Stream<Arguments> exceptionCreateUserAccessRequestDtos() {
        return Stream.of(Arguments.arguments(UserAccessTestDummy.createUserAccessRequestDtoNotnullException()), Arguments.arguments(UserAccessTestDummy.createUserAccessRequestDtoPatternException()), Arguments.arguments(UserAccessTestDummy.createUserAccessRequestDtoMinException()));
    }

    @BeforeEach
    public void setUp() {
        userAccessJpaRepository.deleteAll();
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_생성_성공() throws Exception {
        // given
        CreateUserAccessRequestDto request = validCreateUserAccessRequestDto();
        // when
        ResultActions resultActions = createUserAccessMvcPerform(request);
        UserAccess userAccess = findUserAccessOne();
        // then
        assertUserAccessAndCreateUserAccessRequestDto(userAccess, request);
        resultActions.andExpect(status().isOk());
    }

    private ResultActions createUserAccessMvcPerform(CreateUserAccessRequestDto request) throws Exception {
        return mvc.perform(post("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    private UserAccess findUserAccessOne() {
        return userAccessJpaRepository.findAll()
                .get(0);
    }

    @WithMockUser
    @ParameterizedTest
    @MethodSource("exceptionCreateUserAccessRequestDtos")
    public void 유저_접근_데이터_생성_바인딩_예외(CreateUserAccessRequestDto request) throws Exception {
        // when
        ResultActions resultActions = createUserAccessMvcPerform(request);
        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_생성_허용하지_않는_HTTP_메소드_예외() throws Exception {
        // given
        CreateUserAccessRequestDto request = validCreateUserAccessRequestDto();
        // when
        ResultActions resultActions = createUserAccessPutMvcPerform(request);
        // then
        resultActions.andExpect(status().isMethodNotAllowed());
    }

    private ResultActions createUserAccessPutMvcPerform(CreateUserAccessRequestDto request) throws Exception {
        return mvc.perform(put("/api/user-access").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_아이디로_조회_성공() throws Exception {
        // given
        CreateUserAccessRequestDto request = validCreateUserAccessRequestDto();
        createUserAccessMvcPerform(request);
        Long id = findUserAccessOne().getId();
        // when
        ResultActions resultActions = mvc.perform(get("/api/user-access/" + id));
        // then
        userAccessResultActionsExpect(request, id, resultActions);
    }

    private void userAccessResultActionsExpect(CreateUserAccessRequestDto request, Long id, ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.basicDate").value(request.getBasicDate()))
                .andExpect(jsonPath("$.impCnt").value(request.getImpCnt()))
                .andExpect(jsonPath("$.clickCnt").value(request.getClickCnt()))
                .andExpect(jsonPath("$.convCnt").value(request.getConvCnt()))
                .andExpect(jsonPath("$.sellCost").value(request.getSellCost()))
                .andExpect(jsonPath("$.adspend").value(request.getAdspend()))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_아이디로_조회할때_존재하지_않는_아이디_예외() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(get("/api/user-access/10000"));
        // then
        resultActions.andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_전체조회_성공() throws Exception {
        // given
        CreateUserAccessRequestDto request = validCreateUserAccessRequestDto();
        createUserAccessMvcPerform(request);
        createUserAccessMvcPerform(request);
        // when
        ResultActions resultActions = mvc.perform(get("/api/user-access/all"));
        // then
        resultActions.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_수정_성공() throws Exception {
        // given
        CreateUserAccessRequestDto crateRequest = validCreateUserAccessRequestDto();
        createUserAccessMvcPerform(crateRequest);
        UpdateUserAccessRequestDto updateRequest = UserAccessTestDummy.updateBasicDateUserAccessRequestDto();
        // when
        UserAccess userAccess = findUserAccessOne();
        Assertions.assertThat(userAccess.getBasicDate())
                .isNotEqualTo(updateRequest.getBasicDate());
        ResultActions resultActions = updateUserAccessMvcPerform(updateRequest, userAccess.getId());
        userAccess = findUserAccessOne();
        // then
        Assertions.assertThat(userAccess.getBasicDate())
                .isEqualTo(updateRequest.getBasicDate());
        resultActions.andExpect(status().isOk());
    }

    private ResultActions updateUserAccessMvcPerform(UpdateUserAccessRequestDto request, Long id) throws Exception {
        return mvc.perform(put("/api/user-access/" + id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_수정할때_존재하지_않는_아이디_예외() throws Exception {
        // given
        UpdateUserAccessRequestDto updateRequest = UserAccessTestDummy.updateBasicDateUserAccessRequestDto();
        // when
        ResultActions resultActions = updateUserAccessMvcPerform(updateRequest, 10000L);
        // then
        resultActions.andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_삭제_성공() throws Exception {
        // given
        CreateUserAccessRequestDto crateRequest = validCreateUserAccessRequestDto();
        createUserAccessMvcPerform(crateRequest);
        Long id = findUserAccessOne().getId();
        // when
        Assertions.assertThat(userAccessSize())
                .isEqualTo(1);
        ResultActions resultActions = mvc.perform(delete("/api/user-access/" + id));
        // then
        Assertions.assertThat(userAccessSize())
                .isEqualTo(0);
        resultActions.andExpect(status().isOk());
    }

    private int userAccessSize() {
        return userAccessJpaRepository.findAll()
                .size();
    }

    @WithMockUser
    @Test
    public void 유저_접근_데이터_삭제할때_존재하지_않는_아이디_예외() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(delete("/api/user-access/10000"));
        // then
        resultActions.andExpect(status().isNotFound());
    }
}