package ssu.cttkz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String dn = "";
        Collection<? extends GrantedAuthority> authorities = null;

        if (authentication.getPrincipal() instanceof LdapUserDetails userDetails) {
            dn = userDetails.getDn(); // distinguished name (DN) пользователя
            authorities = userDetails.getAuthorities(); // роли пользователя

            // Вы можете получить дополнительные атрибуты в зависимости от конфигурации вашего LDAP
        }
        return "Hello, " + username + "\n" +
                "Your dn: " + dn + "\n" +
                "Your authorities: " + authorities;
    }
}
