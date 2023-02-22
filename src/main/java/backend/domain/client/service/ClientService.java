package backend.domain.client.service;

import backend.domain.client.dto.AuthoritiesResponseDto;
import backend.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientService {
    private static final String NOT_FOUND_USERNAME = " : 존재하지 않는 username 입니다.";
    private final ClientRepository clientRepository;

    public AuthoritiesResponseDto findAuthorities(String username) {
        List<String> authorities = clientRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + NOT_FOUND_USERNAME))
                .getAuthorities()
                .stream()
                .map(authority -> authority.getAuthorityName())
                .collect(Collectors.toList());
        return new AuthoritiesResponseDto(authorities);
    }
}
