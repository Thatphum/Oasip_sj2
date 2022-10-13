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
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO oldUser) throws Exception{
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

