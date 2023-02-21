package backend.jwt.filter;

import backend.jwt.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

   public static final String AUTHORIZATION_HEADER = "Authorization";
   public static final String BEARER_TOKEN_PREFIX = "Bearer ";
   private static final String INVALID_JWT_TOKEN = "INVALID JWT TOKEN";
   private final TokenProvider tokenProvider;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String authorizationHeader = getAuthorizationHeader(request);
      String token = getToken(authorizationHeader);
      if (token != null) {
         Authentication authentication = tokenProvider.getAuthentication(token);
         SecurityContextHolder.getContext()
                 .setAuthentication(authentication);
      }
      filterChain.doFilter(request, response);
   }

   private String getAuthorizationHeader(HttpServletRequest request) {
      String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
      if (isValidAuthorizationHeader(authorizationHeader)) {
         return authorizationHeader;
      }
      return null;
   }

   private boolean isValidAuthorizationHeader(String authorizationHeader) {
      return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_TOKEN_PREFIX);
   }

   private String getToken(String authorizationHeader) {
      if (authorizationHeader == null) {
         return null;
      }
      String token = authorizationHeader.substring(BEARER_TOKEN_PREFIX.length());
      if (!isValidToken(token)) {
         log.warn(INVALID_JWT_TOKEN);
         return null;
      }
      return token;
   }

   private boolean isValidToken(String token) {
      return StringUtils.hasText(token) && !tokenProvider.isValidTokenWithKey(token);
   }
}
