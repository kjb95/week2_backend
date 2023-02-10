package backend.useraccess.service;

import backend.useraccess.UserAccessTestDummy;
import backend.useraccess.dto.CreateUserAccessRequestDto;
import backend.useraccess.dto.UpdateUserAccessRequestDto;
import backend.useraccess.dto.UserAccessResponseDto;
import backend.useraccess.entity.UserAccess;
import backend.useraccess.repository.UserAccessJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;


@SpringBootTest
@Transactional
class UserAccessServiceTest {

    @Autowired
    UserAccessJpaRepository userAccessJpaRepository;
    @Autowired
    UserAccessService userAccessService;

    @BeforeEach
    void setUp() {
        userAccessJpaRepository.deleteAll();
    }

    @Test
    void createUserAccess_성공() {
        // given
        CreateUserAccessRequestDto request = UserAccessTestDummy.validCreateUserAccessRequestDto();
        // when
        UserAccessResponseDto response = userAccessService.createUserAccess(request);
        UserAccess result = userAccessJpaRepository.findById(response.getId())
                .get();
        // then
        Assertions.assertThat(result.getBasicDate())
                .isEqualTo(request.getBasicDate());
        Assertions.assertThat(result.getImpCnt())
                .isEqualTo(request.getImpCnt());
        Assertions.assertThat(result.getClickCnt())
                .isEqualTo(request.getClickCnt());
        Assertions.assertThat(result.getConvCnt())
                .isEqualTo(request.getConvCnt());
        Assertions.assertThat(result.getSellCost())
                .isEqualTo(request.getSellCost());
        Assertions.assertThat(result.getAdspend())
                .isEqualTo(request.getAdspend());
    }

    @Test
    void findAllSpecificFieldAndFieldName_존재하지_필드_이름_예외() {
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.findAllSpecificFieldAndFieldName("notExist"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findUserAccessById_존재하지_않는_아이디_예외() {
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.findUserAccessById("10000"))
                .isInstanceOf(NoSuchElementException.class);
        ;
    }

    @Test
    void updateUserAccess_존재하지_않는_아이디_예외() {
        // given
        UpdateUserAccessRequestDto updateUserAccessRequestDto = UserAccessTestDummy.updateBasicDateUserAccessRequestDto();
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.updateUserAccess(updateUserAccessRequestDto, "10000"))
                .isInstanceOf(NoSuchElementException.class);
        ;
    }

    @Test
    void deleteUserAccessById_존재하지_않는_아이디_예외() {
        // then
        Assertions.assertThatThrownBy(() -> userAccessService.deleteUserAccessById("10000"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateUserAccess_성공() {
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
    void deleteUserAccessById_성공() {
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