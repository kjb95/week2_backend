package com.example.week2_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ChartDataDictionary {

    IMP_CNT("impCnt", "노출수"),
    CLICK_CNT("clickCnt", "클릭수"),
    CONV_CNT("convCnt", "전환수"),
    SELL_COST("sellCost", "판매금액"),
    ADSPEND("adspend", "광고비");

    private final String english;
    private final String korean;

    public static String findKoreanByEnglish(String english) {
        return Arrays.stream(ChartDataDictionary.values())
                .filter(word -> word.english.equals(english))
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getKorean();
    }
}
