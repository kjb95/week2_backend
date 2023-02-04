package backend.useraccess.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserAccessRequestDto {
    private static final String DATE_REGEX = "^\\d{4}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[01])";
    @Pattern(regexp = DATE_REGEX)
    private String basicDate;
    private Long impCnt;
    private Long clickCnt;
    private Long convCnt;
    private Long sellCost;
    private Long adspend;
}
