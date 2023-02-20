package backend.useraccess.service;

import backend.domain.useraccess.service.UserAccessService;
import backend.useraccess.UserAccessTestDummy;
import backend.domain.useraccess.dto.CreateUserAccessRequestDto;
import backend.domain.useraccess.dto.UpdateUserAccessRequestDto;
import backend.domain.useraccess.dto.UserAccessResponseDto;
import backend.domain.useraccess.entity.UserAccess;
import backend.domain.useraccess.repository.UserAccessJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

import static backend.useraccess.UserAccessTestUtils.assertUserAccessAndCreateUserAccessRequestDto;


@SpringBootTest
@Transactional
class UserAccessServiceTest {

    @Autowired
    private UserAccessJpaRepository userAccessJpaRepository;
    @Autowired
    private UserAccessService userAccessService;

    @BeforeEach
    public void setUp() {
        userAccessJpaRepository.deleteAll();
    }

    @Test
    public void createUserAccess_성공() {
        // given
        CreateUserAccessRequestDto request = UserAccessTestDummy.validCreateUserAccessRequestDto();
        // when
        UserAccessResponseDto response = userAccessService.createUserAccess(request);
        UserAccess result = userAccessJpaRepository.findById(response.getId())
                .get();
        // then
        assertUserAccessAndCreateUserAccessRequestDto(result, request);
    }

    @Test
    public void findAllSpecificFieldAndFieldName_존재하지_필드_이름_예외() {
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.findAllSpecificFieldAndFieldName("notExist"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findUserAccessById_존재하지_않는_아이디_예외() {
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.findUserAccessById("10000"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void updateUserAccess_존재하지_않는_아이디_예외() {
        // given
        UpdateUserAccessRequestDto updateUserAccessRequestDto = UserAccessTestDummy.updateBasicDateUserAccessRequestDto();
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.updateUserAccess(updateUserAccessRequestDto, "10000"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void deleteUserAccessById_존재하지_않는_아이디_예외() {
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.deleteUserAccessById("10000"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void updateUserAccess_성공() {
        // given
        CreateUserAccessRequestDto createRequest = UserAccessTestDummy.validCreateUserAccessRequestDto();
        UserAccessResponseDto createResponse = userAccessService.createUserAccess(createRequest);
        String id = String.valueOf(createResponse.getId());
        UpdateUserAccessRequestDto updateRequest = UserAccessTestDummy.updateBasicDateUserAccessRequestDto();
        // when
        UserAccessResponseDto output = userAccessService.findUserAccessById(id);
        Assertions.assertThat(output.getBasicDate())
                .isNotEqualTo(updateRequest.getBasicDate());
        userAccessService.updateUserAccess(updateRequest, id);
        output = userAccessService.findUserAccessById(id);
        // then
        Assertions.assertThat(output.getBasicDate())
                .isEqualTo(updateRequest.getBasicDate());
    }

    @Test
    public void deleteUserAccessById_성공() {
        // given
        CreateUserAccessRequestDto createRequest = UserAccessTestDummy.validCreateUserAccessRequestDto();
        UserAccessResponseDto createResponse = userAccessService.createUserAccess(createRequest);
        String id = String.valueOf(createResponse.getId());
        // when
        Assertions.assertThatCode(() -> userAccessService.findUserAccessById(id))
                .doesNotThrowAnyException();
        userAccessService.deleteUserAccessById(id);
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.findUserAccessById(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}