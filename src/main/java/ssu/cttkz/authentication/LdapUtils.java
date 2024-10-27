package ssu.cttkz.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LdapUtils {
    private static String getDn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof LdapUserDetailsImpl ldapUserDetails) {
            return ldapUserDetails.getDn();
        }

        return null;
    }

    public static String getFullName() {
        String dn = getDn();
        if (dn == null) return null;

        Pattern namePattern = Pattern.compile("CN=([^,]+)");
        Matcher nameMatcher = namePattern.matcher(dn);

        return nameMatcher.find() ? nameMatcher.group(1) : null;
    }

    public static String getDepartment() {
        String dn = getDn();
        if (dn == null) return null;

        Pattern departmentPattern = Pattern.compile("OU=([^,]+)");
        Matcher departmentMatcher = departmentPattern.matcher(dn);

        return departmentMatcher.find() ? departmentMatcher.group(1) : null;
    }
}
