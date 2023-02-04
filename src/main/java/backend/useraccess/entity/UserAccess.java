package backend.useraccess.entity;

import backend.useraccess.dto.UserAccessDto;
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

    public void update(UserAccessDto userAccessDto) {
        this.basicDate = userAccessDto.getBasicDate() == null ? this.basicDate : userAccessDto.getBasicDate();
        this.impCnt = userAccessDto.getImpCnt() == null ? this.impCnt : userAccessDto.getImpCnt();
        this.clickCnt = userAccessDto.getClickCnt() == null ? this.clickCnt : userAccessDto.getClickCnt();
        this.convCnt = userAccessDto.getConvCnt() == null ? this.convCnt : userAccessDto.getConvCnt();
        this.sellCost = userAccessDto.getSellCost() == null ? this.sellCost : userAccessDto.getSellCost();
        this.adspend = userAccessDto.getAdspend() == null ? this.adspend : userAccessDto.getAdspend();
    }
}
