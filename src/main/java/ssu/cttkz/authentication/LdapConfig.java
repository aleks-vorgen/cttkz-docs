package ssu.cttkz.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
public class LdapConfig {

    @Value("${ldap.user-search-filter.regexp}")
    private String userSearchFilter;
    @Value("${ldap.domain}")
    private String ldapDomain;
    @Value("${ldap.url}")
    private String ldapUrl;

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapUrl);
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUseAuthenticationRequestCredentials(true);

        if (userSearchFilter != null && !userSearchFilter.trim().isEmpty()) {
            provider.setSearchFilter(userSearchFilter);
        }
        return provider;
    }
}
