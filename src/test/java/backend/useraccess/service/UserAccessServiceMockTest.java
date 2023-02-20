package backend.useraccess.service;

import backend.domain.useraccess.service.UserAccessService;
import backend.useraccess.UserAccessTestDummy;
import backend.domain.useraccess.dto.ChartDataDto;
import backend.domain.useraccess.dto.UserAccessResponseDto;
import backend.domain.useraccess.entity.UserAccess;
import backend.domain.useraccess.enums.ChartDataDictionary;
import backend.domain.useraccess.repository.UserAccessJpaRepository;
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

import static backend.useraccess.UserAccessTestUtils.assertUserAccessAndUserAccessResponseDto;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@Transactional
public class UserAccessServiceMockTest {
    @Mock
    private UserAccessJpaRepository userAccessJpaRepository;

    @InjectMocks
    private UserAccessService userAccessService;

    @BeforeEach
    public void setUp() {
        userAccessJpaRepository.deleteAll();
    }

    @Test
    public void findAllUserAccessDto_성공() {
        // given
        List<UserAccess> expectedUserAccesses = UserAccessTestDummy.createUserAccesses(10);
        given(userAccessJpaRepository.findAll()).willReturn(expectedUserAccesses);
        // when
        List<UserAccessResponseDto> outputs = userAccessService.findAllUserAccessDto();
        // then
        IntStream.range(0, 10)
                .forEach(i -> assertUserAccessAndUserAccessResponseDto(expectedUserAccesses.get(i), outputs.get(i)));
    }

    @Test
    public void findAllSpecificFieldAndFieldName_성공() {
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
    public void findUserAccessById_성공() {
        // given
        UserAccess expectedUserAccess = UserAccessTestDummy.createUserAccess(1L, 10L);
        given(userAccessJpaRepository.findById(anyLong())).willReturn(Optional.ofNullable(expectedUserAccess));
        // when
        UserAccessResponseDto output = userAccessService.findUserAccessById("1");
        // then
        assertUserAccessAndUserAccessResponseDto(expectedUserAccess, output);
    }
}
