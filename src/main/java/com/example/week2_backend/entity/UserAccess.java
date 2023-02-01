package com.example.week2_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class UserAccess {

    private Long id;
    private String basicDate;
    private Long impCnt;
    private Long clickCnt;
    private Long sellCost;
    private Long adspend;
}
