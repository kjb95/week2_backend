package backend.domain.client.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritiesResponseDto {
    List<String> authorities;
}
