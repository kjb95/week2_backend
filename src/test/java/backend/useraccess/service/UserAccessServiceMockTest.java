package backend.useraccess.service;

import backend.useraccess.UserAccessTestDummy;
import backend.useraccess.dto.ChartDataDto;
import backend.useraccess.dto.UserAccessResponseDto;
import backend.useraccess.entity.UserAccess;
import backend.useraccess.enums.ChartDataDictionary;
import backend.useraccess.repository.UserAccessJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class UserAccessServiceMockTest {
    @Mock
    UserAccessJpaRepository userAccessJpaRepository;

    @InjectMocks
    UserAccessService userAccessService;

    @BeforeEach
    void setUp() {
        userAccessJpaRepository.deleteAll();
    }

    @Test
    void findAllUserAccessDto_성공() {
        // given
        List<UserAccess> expectedUserAccesses = UserAccessTestDummy.createUserAccesses(10);
        given(userAccessJpaRepository.findAll()).willReturn(expectedUserAccesses);
        // when
        List<UserAccessResponseDto> outputs = userAccessService.findAllUserAccessDto();
        // then
        IntStream.range(0, 10)
                .forEach(i -> assertUserAccessAndUserAccessResponseDto(expectedUserAccesses.get(i), outputs.get(i)));
    }

    private static void assertUserAccessAndUserAccessResponseDto(UserAccess expectedUserAccess, UserAccessResponseDto output) {
        Assertions.assertThat(output.getId())
                .isEqualTo(expectedUserAccess.getId());
        Assertions.assertThat(output.getBasicDate())
                .isEqualTo(expectedUserAccess.getBasicDate());
        Assertions.assertThat(output.getImpCnt())
                .isEqualTo(expectedUserAccess.getImpCnt());
        Assertions.assertThat(output.getClickCnt())
                .isEqualTo(expectedUserAccess.getClickCnt());
        Assertions.assertThat(output.getConvCnt())
                .isEqualTo(expectedUserAccess.getConvCnt());
        Assertions.assertThat(output.getSellCost())
                .isEqualTo(expectedUserAccess.getSellCost());
        Assertions.assertThat(output.getAdspend())
                .isEqualTo(expectedUserAccess.getAdspend());
    }

    @Test
    void findAllSpecificFieldAndFieldName_성공() {
        // given
        List<UserAccess> expectedUserAccesses = UserAccessTestDummy.createUserAccesses(10);
        given(userAccessJpaRepository.findAll()).willReturn(expectedUserAccesses);
        // when
        ChartDataDto output = userAccessService.findAllSpecificFieldAndFieldName("impCnt");
        String name = output.getName();
        List<Long> datas = output.getData();
        // then
        Assertions.assertThat(name)
                .isEqualTo(ChartDataDictionary.findKoreanByEnglish("impCnt"));
        IntStream.range(0, 10)
                .forEach(i -> assertFindAllSpecificFieldAndFieldName(expectedUserAccesses.get(i), datas.get(i)));
    }

    private void assertFindAllSpecificFieldAndFieldName(UserAccess expectedUserAccess, long data) {
        Long expectedImpCnt = expectedUserAccess.getImpCnt();
        Assertions.assertThat(data)
                .isEqualTo(expectedImpCnt);
    }

    @Test
    void findUserAccessById_성공() {
        // given
        UserAccess expectedUserAccess = UserAccessTestDummy.createUserAccess(1L, 10L);
        given(userAccessJpaRepository.findById(anyLong())).willReturn(Optional.ofNullable(expectedUserAccess));
        // when
        UserAccessResponseDto output = userAccessService.findUserAccessById("1");
        // then
        assertUserAccessAndUserAccessResponseDto(expectedUserAccess, output);
    }
}
