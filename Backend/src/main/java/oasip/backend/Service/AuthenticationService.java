package oasip.backend.Service;

<<<<<<< HEAD
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import oasip.backend.DTOs.Authentication.LoginDTO;
import oasip.backend.DTOs.User.UserListAllDto;
import oasip.backend.Enitities.User;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
=======
import oasip.backend.Config.JwtTokenUtil;
import oasip.backend.Config.Jwts.AuthenticationUser;
import oasip.backend.Config.Jwts.JwtUserDetailsService;
import oasip.backend.DTOs.Authentication.LoginDTO;
import oasip.backend.DTOs.Authentication.TokenDto;
import oasip.backend.DTOs.Jwt.JwtResponse;
import oasip.backend.Enitities.User;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
>>>>>>> 0a746d519c05a7f45ccf7b9d2d4ebaa75bbee663
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
<<<<<<< HEAD

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;
=======
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtUserDetailsService jwtUserDetailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

>>>>>>> 0a746d519c05a7f45ccf7b9d2d4ebaa75bbee663

//    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 26, 1, 65536, 10);
    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

<<<<<<< HEAD
    public boolean match(LoginDTO oldUser) {
        User user = userRepository.findByEmail(oldUser.getEmail());
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist");
        }
        if(!(passwordEncoder.matches(oldUser.getPassword(),user.getPassword()))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password NOT Matched");
        }
        return true;
    }

//    public boolean match() {
//        Argon2 argon2 = Argon2Factory.create(
//                Argon2Factory.Argon2Types.ARGON2id,
//                10,
//                32);
//        String hash = argon2.hash(30, 65536, 1,"password");
////        System.out.println(hash);
////        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
////        String s = passwordEncoder.encode("password");
//        boolean success = argon2.verify(hash, "password");
////        System.out.println(success);
//
////        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 26, 1, 65536, 10);
//        String result = encoder.encode("ram123sdadsadsadsa");
//        System.out.println(result);
//        System.out.println(encoder.matches("ram123", result));
////        System.out.println(result);
//        return true;
//    }
=======
    public ResponseEntity<?> match(LoginDTO oldUser) {
        try {
            User user = userRepository.findByEmail(oldUser.getEmail());
            if(user != null){
                //check Password is match
                if(!(passwordEncoder.matches(oldUser.getPassword(),user.getPassword()))){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password NOT Matched");
                }
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                final AuthenticationUser authenticationUser = jwtUserDetailService.loadUserByUsername(user.getEmail());
                String jwt = jwtTokenUtil.generateToken(authenticationUser);
                List<String> roles = authenticationUser.getAuthorities().stream().map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                String jwtRefreshToken = jwtTokenUtil.generateRefreshToken(authenticationUser);
                return ResponseEntity.ok(new JwtResponse(jwt, jwtRefreshToken, authenticationUser.getUsername(), roles));
            }else
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist");
        }catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    public ResponseEntity<?> getRefreshToken(String jwtRefreshToken){
        try{
            String username = jwtTokenUtil.getUsernameFromToken(jwtRefreshToken);
            AuthenticationUser authenticationUser = this.jwtUserDetailService.loadUserByUsername(username);
            String jwt = jwtTokenUtil.generateToken(authenticationUser);
            List<String> roles = authenticationUser.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt, jwtRefreshToken, authenticationUser.getUsername(), roles));
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"JWT Refresh Token has expired",ex);
        }



    }

>>>>>>> 0a746d519c05a7f45ccf7b9d2d4ebaa75bbee663
}
