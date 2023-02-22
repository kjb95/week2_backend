package backend.domain.client.controller;

import backend.domain.client.dto.AuthoritiesResponseDto;
import backend.domain.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;

    /**
     * 유저가 가진 모든 권한 조회
     */
    @GetMapping("/{username}")
    public ResponseEntity<AuthoritiesResponseDto> findAuthorities(@PathVariable String username) {
        AuthoritiesResponseDto authoritiesResponseDto = clientService.findAuthorities(username);
        return ResponseEntity.ok()
                .body(authoritiesResponseDto);
    }
}
