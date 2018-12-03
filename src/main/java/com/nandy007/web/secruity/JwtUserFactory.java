package com.nandy007.web.secruity;
import java.util.List;
import java.util.stream.Collectors;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nandy007.web.model.Authority;
import com.nandy007.web.model.UserAuthority;

public class JwtUserFactory {
	private JwtUserFactory() {
    }

    public static JwtUser create(UserAuthority ua) {
        return new JwtUser(
        		ua.getUser().getId(),
        		ua.getUser().getUsername(),
        		ua.getUser().getPassword(),
                mapToGrantedAuthorities(ua.getAuthorityList()),
                ua.getUser().getLastPasswordResetDate()
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
}
