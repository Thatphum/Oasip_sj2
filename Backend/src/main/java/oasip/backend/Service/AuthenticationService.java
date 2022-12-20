package oasip.backend.Service;

import oasip.backend.DTOs.Authentication.Jwt.userResponce;
import oasip.backend.Exception.ErrorResponse;
import oasip.backend.Config.JwtTokenUtil;
import oasip.backend.Config.Jwts.AuthenticationUser;
import oasip.backend.Config.Jwts.JwtUserDetailsService;
import oasip.backend.DTOs.Authentication.LoginDTO;
import oasip.backend.DTOs.Authentication.Jwt.JwtResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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



//    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 26, 1, 65536, 10);
    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    public ResponseEntity<?> match(LoginDTO oldUser) {
        Authentication authentications = SecurityContextHolder.getContext().getAuthentication();
        if(authentications.getName().contains("anonymousUser"))
        try {
            User user = userRepository.findByEmail(oldUser.getEmail());
            if(user != null){
                //check Password is match
                if(!(passwordEncoder.matches(oldUser.getPassword(),user.getPassword()))){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(HttpStatus.UNAUTHORIZED,"Password NOT Matched"));
                }
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                final AuthenticationUser authenticationUser = jwtUserDetailService.loadUserByEmail(user.getEmail());
                System.out.println(authenticationUser);
                String jwt = jwtTokenUtil.generateToken(authenticationUser);
                List<String> roles = authenticationUser.getAuthorities().stream().map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                String jwtRefreshToken = jwtTokenUtil.generateRefreshToken(authenticationUser);
                return ResponseEntity.ok(new JwtResponse(jwt,jwtTokenUtil.getExpirationDateFromToken(jwt), jwtRefreshToken , new userResponce(authenticationUser.getEmail(),user.getName(), roles.get(0)) ));
            }else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND,"A user with the specified email DOES NOT exist"));
        }catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN,"Access denied"));
    }

    public ResponseEntity<?> getRefreshToken(String jwtRefreshToken){
        System.out.println("dsadads");
        try{
            String email = jwtTokenUtil.getUsernameFromToken(jwtRefreshToken);
            AuthenticationUser authenticationUser = this.jwtUserDetailService.loadUserByEmail(email);
            System.out.println("dsada");

            String jwt = jwtTokenUtil.generateToken(authenticationUser);
            List<String> roles = authenticationUser.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,jwtTokenUtil.getExpirationDateFromToken(jwt), jwtRefreshToken , new userResponce(authenticationUser.getEmail(),authenticationUser.getUsername(), roles.get(0))));
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"JWT Refresh Token has expired",ex);
        }
    }

}
