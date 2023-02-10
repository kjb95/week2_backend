package backend.useraccess;

import backend.useraccess.dto.CreateUserAccessRequestDto;
import backend.useraccess.dto.UpdateUserAccessRequestDto;
import backend.useraccess.entity.UserAccess;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UserAccessTestDummy {

    public static CreateUserAccessRequestDto validCreateUserAccessRequestDto() {
        return CreateUserAccessRequestDto.builder()
                .basicDate("2023.01.01")
                .impCnt(123L)
                .clickCnt(234L)
                .convCnt(345L)
                .sellCost(456L)
                .adspend(567L)
                .build();
    }

    public static CreateUserAccessRequestDto createUserAccessRequestDtoNotnullException() {
        return CreateUserAccessRequestDto.builder()
                .basicDate("2023.01.01")
                .impCnt(123L)
                .clickCnt(234L)
                .convCnt(345L)
                .sellCost(456L)
//                .adspend(567L)  @Notnull
                .build();
    }

    public static CreateUserAccessRequestDto createUserAccessRequestDtoPatternException() {
        return CreateUserAccessRequestDto.builder()
                .basicDate("2023.01.012345")  // @Pattern
                .impCnt(123L)
                .clickCnt(234L)
                .convCnt(345L)
                .sellCost(456L)
                .adspend(567L)
                .build();
    }

    public static CreateUserAccessRequestDto createUserAccessRequestDtoMinException() {
        return CreateUserAccessRequestDto.builder()
                .basicDate("2023.01.01")
                .impCnt(123L)
                .clickCnt(234L)
                .convCnt(345L)
                .sellCost(456L)
                .adspend(-1L)  // @Min
                .build();
    }

    public static UpdateUserAccessRequestDto updateBasicDateUserAccessRequestDto() {
        return UpdateUserAccessRequestDto.builder()
                .basicDate("1111.11.11")
                .build();
    }

    public static UserAccess createUserAccess(Long id, Long value) {
        return UserAccess.builder()
                .id(id)
                .basicDate("2023.01.01")
                .impCnt(value)
                .clickCnt(value)
                .convCnt(value)
                .sellCost(value)
                .adspend(value)
                .build();
    }

    public static List<UserAccess> createUserAccesses(int n) {
        return LongStream.rangeClosed(1, n)
                .mapToObj((i) -> createUserAccess(i, i))
                .collect(Collectors.toList());
    }
}