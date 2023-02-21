package backend.jwt.controller;

import backend.jwt.dto.AuthenticateClientRequestDto;
import backend.jwt.dto.JwtTokenDto;
import backend.jwt.filter.JwtFilter;
import backend.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    private final JwtService jwtService;

    /**
     * 유효한 사용자면 jwt 토큰 반환
     */
    @PostMapping
    public ResponseEntity<JwtTokenDto> authenticateClient(@RequestBody @Validated AuthenticateClientRequestDto authenticateClientRequestDto, HttpServletResponse response) {
        String jwt = jwtService.authenticateClient(authenticateClientRequestDto);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_TOKEN_PREFIX + jwt);
        return ResponseEntity.ok()
                .body(new JwtTokenDto(jwt));
    }
}
