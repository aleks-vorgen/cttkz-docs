package ssu.cttkz.authentication.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.Permission;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private Long expiration;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JWTTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, Permission permission) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String dn = "";
        Collection<? extends GrantedAuthority> authorities = null;

        if (authentication.getPrincipal() instanceof LdapUserDetails userDetails) {
            dn = userDetails.getDn(); // distinguished name (DN) пользователя
            authorities = userDetails.getAuthorities(); // роли пользователя

            // Вы можете получить дополнительные атрибуты в зависимости от конфигурации вашего LDAP
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().signingKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().key(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private String getPermissionName(Permission permission) {
        return permission.getPermission();
    }
}
