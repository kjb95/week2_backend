package backend.useraccess.controller;

import backend.useraccess.dto.CreateUserAccessRequestDto;
import backend.useraccess.dto.UpdateUserAccessRequestDto;

class UserAccessControllerTestDummy {

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

    public static UpdateUserAccessRequestDto validUpdateUserAccessRequestDto() {
        return UpdateUserAccessRequestDto.builder()
                .basicDate("1111.11.11")
                .build();
    }
}