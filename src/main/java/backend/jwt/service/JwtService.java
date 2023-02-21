package backend.jwt.service;

import backend.jwt.dto.AuthenticateClientRequestDto;
import backend.jwt.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    /**
     * 유효한 사용자면 jwt 토큰 반환
     * 유효하지 않은 사용자면 authenticate() 에서 예외를 던짐
     *
     * @see backend.security.service.CustomUserDetailsService#loadUserByUsername(String)
     * authenticate() 호출시 CustomUserDetailsService.loadUserByUsername()가 호출됨
     */
    public String authenticateClient(AuthenticateClientRequestDto authenticateClientRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticateClientRequestDto.getUsername(), authenticateClientRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }
}
