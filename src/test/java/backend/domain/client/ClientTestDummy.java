package backend.domain.client;

import backend.domain.client.entity.Authority;
import backend.domain.client.entity.Client;
import backend.jwt.dto.AuthenticateClientRequestDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientTestDummy {

    public static Client createAdminClient() {
        return Client.builder()
                .username("admin")
                .password("$2y$04$EYZTjFPQeBLc9Xb8nExTE.r48sVj7K19sRq/YB9rTOQf54tPKs2ei")
                .authorities(createAdminAuthority())
                .build();
    }

    private static Set<Authority> createAdminAuthority() {
        return new HashSet<>(List.of(new Authority("ROLE_USER"), new Authority("ROLE_MANAGER"), new Authority("ROLE_ADMIN")));
    }

    public static AuthenticateClientRequestDto createAdminAuthenticateRequestDto() {
        return AuthenticateClientRequestDto.builder()
                .username("admin")
                .password("admin")
                .build();
    }
}
