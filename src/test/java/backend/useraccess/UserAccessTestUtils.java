package backend.useraccess;

import backend.useraccess.dto.CreateUserAccessRequestDto;
import backend.useraccess.dto.UserAccessResponseDto;
import backend.useraccess.entity.UserAccess;
import org.assertj.core.api.Assertions;

public class UserAccessTestUtils {
    public static void assertUserAccessAndCreateUserAccessRequestDto(UserAccess userAccess, CreateUserAccessRequestDto createUserAccessRequestDto) {
        Assertions.assertThat(userAccess.getBasicDate())
                .isEqualTo(createUserAccessRequestDto.getBasicDate());
        Assertions.assertThat(userAccess.getImpCnt())
                .isEqualTo(createUserAccessRequestDto.getImpCnt());
        Assertions.assertThat(userAccess.getClickCnt())
                .isEqualTo(createUserAccessRequestDto.getClickCnt());
        Assertions.assertThat(userAccess.getConvCnt())
                .isEqualTo(createUserAccessRequestDto.getConvCnt());
        Assertions.assertThat(userAccess.getSellCost())
                .isEqualTo(createUserAccessRequestDto.getSellCost());
        Assertions.assertThat(userAccess.getAdspend())
                .isEqualTo(createUserAccessRequestDto.getAdspend());
    }

    public static void assertUserAccessAndUserAccessResponseDto(UserAccess expectedUserAccess, UserAccessResponseDto output) {
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

}
