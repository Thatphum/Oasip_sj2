package oasip.backend.Controller;

import oasip.backend.Config.JwtTokenUtil;
import oasip.backend.DTOs.Authentication.LoginDTO;
import oasip.backend.DTOs.Authentication.TokenDto;
import oasip.backend.DTOs.Category.CategoryListAllDto;
import oasip.backend.DTOs.User.UserCreateDto;
import oasip.backend.Service.AuthenticationService;
import oasip.backend.Service.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/match")
public class AuthenticationController {



    @Autowired
    private AuthenticationService service;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto getAllCategory(@Valid @RequestBody LoginDTO oldUser){
        return service.match(oldUser);
    }
}


//import java.util.Objects;
//
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.http.ResponseEntity;
//        import org.springframework.security.authentication.AuthenticationManager;
//        import org.springframework.security.authentication.BadCredentialsException;
//        import org.springframework.security.authentication.DisabledException;
//        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//        import org.springframework.security.core.userdetails.UserDetails;
//        import org.springframework.web.bind.annotation.CrossOrigin;
//        import org.springframework.web.bind.annotation.RequestBody;
//        import org.springframework.web.bind.annotation.RequestMapping;
//        import org.springframework.web.bind.annotation.RequestMethod;
//        import org.springframework.web.bind.annotation.RestController;
//        import com.javainuse.service.JwtUserDetailsService;
//
//
//        import com.javainuse.config.JwtTokenUtil;
//        import com.javainuse.model.JwtRequest;
//        import com.javainuse.model.JwtResponse;
//
//@RestController
//@CrossOrigin
//public class JwtAuthenticationController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private JwtUserDetailsService userDetailsService;
//
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(authenticationRequest.getUsername());
//
//        final String token = jwtTokenUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
//}
