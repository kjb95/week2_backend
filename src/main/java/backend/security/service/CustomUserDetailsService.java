package backend.security.service;

import backend.domain.client.entity.Client;
import backend.domain.client.repository.ClientRepository;
import backend.jwt.dto.AuthenticateClientRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private static final String NOT_FOUND_USERNAME = " : 존재하지 않는 username 입니다.";
    private final ClientRepository clientRepository;

    /**
     * 유효한 사용자면 UserDetails 반환
     * 유효하지 않은 사용자면 UsernameNotFoundException 에러발생
     *
     * @see backend.jwt.service.JwtService#authenticateClient(AuthenticateClientRequestDto)
     * JwtService.authenticate() 호출시 loadUserByUsernae()가 호출됨
     * @see backend.common.controller.ControllerAdvice#handleBadCredentialsException(BadCredentialsException)
     * 시큐리티가 UsernameNotFoundException 예외를 캐치하고, BadCredentialsException 예외를 던짐
     * 컨트롤러 어드바이스에서 BadCredentialsException 예외를 처리
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return clientRepository.findOneWithAuthoritiesByUsername(username)
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException(username + NOT_FOUND_USERNAME));
    }

    private User createUser(Client user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
