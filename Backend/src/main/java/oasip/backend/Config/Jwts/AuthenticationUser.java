package oasip.backend.Config.Jwts;

import oasip.backend.Enum.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthenticationUser implements UserDetails {
    private final String username;
    private final String email;
    private final String password;
    private final Collection<Role> authorities;

    public AuthenticationUser( String email, String username, String password, Collection<Role> authorities) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        for (Role c : this.authorities)
//            System.out.println(c.getAuthority());
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail() { return this.email; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
