package backend.domain.useraccess.entity;

import backend.domain.useraccess.dto.UpdateUserAccessRequestDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class UserAccess {

    @Id
    @GeneratedValue
    private Long id;
    private String basicDate;
    private Long impCnt;
    private Long clickCnt;
    private Long convCnt;
    private Long sellCost;
    private Long adspend;

    public void update(UpdateUserAccessRequestDto updateUserAccessRequestDto) {
        this.basicDate = updateUserAccessRequestDto.getBasicDate() == null ? this.basicDate : updateUserAccessRequestDto.getBasicDate();
        this.impCnt = updateUserAccessRequestDto.getImpCnt() == null ? this.impCnt : updateUserAccessRequestDto.getImpCnt();
        this.clickCnt = updateUserAccessRequestDto.getClickCnt() == null ? this.clickCnt : updateUserAccessRequestDto.getClickCnt();
        this.convCnt = updateUserAccessRequestDto.getConvCnt() == null ? this.convCnt : updateUserAccessRequestDto.getConvCnt();
        this.sellCost = updateUserAccessRequestDto.getSellCost() == null ? this.sellCost : updateUserAccessRequestDto.getSellCost();
        this.adspend = updateUserAccessRequestDto.getAdspend() == null ? this.adspend : updateUserAccessRequestDto.getAdspend();
    }
}
