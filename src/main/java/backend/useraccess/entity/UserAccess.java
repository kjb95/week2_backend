package backend.useraccess.entity;

import backend.useraccess.dto.UserAccessResponseDto;
import lombok.*;

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
    private Long convCnt;
    private Long sellCost;
    private Long adspend;

    public void update(UserAccessResponseDto userAccessResponseDto) {
        this.basicDate = userAccessResponseDto.getBasicDate() == null ? this.basicDate : userAccessResponseDto.getBasicDate();
        this.impCnt = userAccessResponseDto.getImpCnt() == null ? this.impCnt : userAccessResponseDto.getImpCnt();
        this.clickCnt = userAccessResponseDto.getClickCnt() == null ? this.clickCnt : userAccessResponseDto.getClickCnt();
        this.convCnt = userAccessResponseDto.getConvCnt() == null ? this.convCnt : userAccessResponseDto.getConvCnt();
        this.sellCost = userAccessResponseDto.getSellCost() == null ? this.sellCost : userAccessResponseDto.getSellCost();
        this.adspend = userAccessResponseDto.getAdspend() == null ? this.adspend : userAccessResponseDto.getAdspend();
    }
}
