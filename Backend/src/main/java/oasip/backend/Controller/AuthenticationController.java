package oasip.backend.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import oasip.backend.Config.JwtTokenUtil;
import oasip.backend.Config.Jwts.AuthenticationUser;
import oasip.backend.Config.Jwts.JwtUserDetailsService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationService service;

    @Autowired
    JwtUserDetailsService jwtUserDetailService;

    @PostMapping("/match")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO oldUser){
        return service.match(oldUser);
    }

//    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    @GetMapping("/refreshtoken")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        String jwtRefreshToken = extractJwtFromRequest(request);
        return service.getRefreshToken(jwtRefreshToken);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

//    @RequestMapping("/logout")
//    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        jwtTokenUtil.invalidateRelatedTokens(httpServletRequest);
////        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
//        return "redirect:/";
//    }
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
