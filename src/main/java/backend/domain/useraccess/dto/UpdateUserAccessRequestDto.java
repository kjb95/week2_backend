package backend.domain.useraccess.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserAccessRequestDto {
    private static final String DATE_REGEX = "^\\d{4}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[01])";
    @Pattern(regexp = DATE_REGEX)
    private String basicDate;
    @Min(0)
    private Long impCnt;
    @Min(0)
    private Long clickCnt;
    @Min(0)
    private Long convCnt;
    @Min(0)
    private Long sellCost;
    @Min(0)
    private Long adspend;
}
