package backend.useraccess.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Getter
public enum ChartDataDictionary {

    IMP_CNT("impCnt", "노출수"),
    CLICK_CNT("clickCnt", "클릭수"),
    CONV_CNT("convCnt", "전환수"),
    SELL_COST("sellCost", "판매금액"),
    ADSPEND("adspend", "광고비");

    private static final String NOT_FOUND_CHART_FIELD_NAME = "존재하지 않는 차트 데이터의 필드 이름 입니다.";
    private final String english;
    private final String korean;

    public static String findKoreanByEnglish(String english) {
        return Arrays.stream(ChartDataDictionary.values())
                .filter(word -> word.english.equals(english))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_CHART_FIELD_NAME))
                .getKorean();
    }
}
