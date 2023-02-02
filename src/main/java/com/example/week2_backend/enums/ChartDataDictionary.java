package com.example.week2_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ChartDataDictionary {

    IMP_CNT("impCnt", "노출수"),
    CLICK_CNT("clickCnt", "클릭수");

    private final String english;
    private final String korean;

    public static String findKoreanByEnglish(String english) {
        return Arrays.stream(ChartDataDictionary.values())
                .filter(word -> word.english.equals(english))
                .findAny()
                .orElseThrow(() -> new NullPointerException())
                .getKorean();
    }
}
