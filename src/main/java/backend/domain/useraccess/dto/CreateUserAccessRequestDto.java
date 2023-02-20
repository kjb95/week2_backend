package backend.domain.useraccess.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserAccessRequestDto {
    private static final String DATE_REGEX = "^\\d{4}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[01])$";
    @NotNull
    @Pattern(regexp = DATE_REGEX)
    private String basicDate;
    @NotNull
    @Min(0)
    private Long impCnt;
    @NotNull
    @Min(0)
    private Long clickCnt;
    @NotNull
    @Min(0)
    private Long convCnt;
    @NotNull
    @Min(0)
    private Long sellCost;
    @NotNull
    @Min(0)
    private Long adspend;
}
