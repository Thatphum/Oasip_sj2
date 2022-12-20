package oasip.backend.Config.Jwts;

import oasip.backend.Enitities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JwtUserDetails extends UserDetailsService {
    @Override
    AuthenticationUser loadUserByUsername(String s) throws UsernameNotFoundException;

    AuthenticationUser loadUserByEmail(String s) throws UsernameNotFoundException;

    User getUserByUsername(String s) throws UsernameNotFoundException;
}
