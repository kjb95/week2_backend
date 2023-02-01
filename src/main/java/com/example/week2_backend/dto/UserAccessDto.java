package com.example.week2_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccessDto {

    private String basicDate;
    private Long impCnt;
    private Long clickCnt;
    private Long sellCost;
    private Long adspend;
}