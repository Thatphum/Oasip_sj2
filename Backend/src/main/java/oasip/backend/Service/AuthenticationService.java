package oasip.backend.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import net.minidev.json.JSONObject;
import oasip.backend.Config.JwtTokenUtil;
import oasip.backend.Config.JwtUserDetailsService;
import oasip.backend.DTOs.Authentication.LoginDTO;
import oasip.backend.DTOs.User.UserListAllDto;
import oasip.backend.Enitities.User;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

//    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 26, 1, 65536, 10);
    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    public String match(LoginDTO oldUser) {
        System.out.println("dskaldks");
        User user = userRepository.findByEmail(oldUser.getEmail());
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist");
        }
        if(!(passwordEncoder.matches(oldUser.getPassword(),user.getPassword()))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password NOT Matched");
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(oldUser.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

//        return ResponseEntity.ok(new JwtResponse(token));
        return  token;
    }



}
