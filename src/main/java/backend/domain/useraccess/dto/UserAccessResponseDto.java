package backend.domain.useraccess.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccessResponseDto {

    private Long id;
    private String basicDate;
    private Long impCnt;
    private Long clickCnt;
    private Long convCnt;
    private Long sellCost;
    private Long adspend;
}
