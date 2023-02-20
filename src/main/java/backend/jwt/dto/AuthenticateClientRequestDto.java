package backend.jwt.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateClientRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
