package oasip.backend.Config.Jwts;


import oasip.backend.Enitities.User;
import oasip.backend.Enum.Role;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements JwtUserDetails {

    @Autowired
    private UserRepository userRepository;

    private Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();

    @Override
    public AuthenticationUser loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);
        if (user != null){
            Role role = new Role(user.getRole().toString());
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            return  new AuthenticationUser(user.getEmail(),user.getName() , argon2PasswordEncoder.encode(user.getPassword()), roles);
        }

        throw new UsernameNotFoundException("User not found with Name: " + s);
    }

    @Override
    public AuthenticationUser loadUserByEmail(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if (user != null){
            Role role = new Role(user.getRole().toString());
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            return  new AuthenticationUser(user.getEmail(),user.getName() , argon2PasswordEncoder.encode(user.getPassword()), roles);
        }

        throw new UsernameNotFoundException("User not found with Email: " + s);
    }


    @Override
    public User getUserByUsername(String s) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(s);
        return user;
    }
}
