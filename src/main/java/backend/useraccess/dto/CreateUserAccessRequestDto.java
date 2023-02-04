package backend.useraccess.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserAccessRequestDto {
    private static final String DATE_REGEX = "^\\d{4}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[01])";
    @NotNull
    @Pattern(regexp = DATE_REGEX)
    private String basicDate;
    @NotNull
    private Long impCnt;
    @NotNull
    private Long clickCnt;
    @NotNull
    private Long convCnt;
    @NotNull
    private Long sellCost;
    @NotNull
    private Long adspend;
}
