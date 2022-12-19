package oasip.backend.DTOs.Authentication.Jwt;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private Date accessTokenExp;
    private String refreshToken;
//    private String type = "Bearer";
    private userResponce user;

    public JwtResponse(String accessToken, Date accessTokenExp,String refreshToken,userResponce user) {
        this.accessToken = accessToken;
        this.accessTokenExp = accessTokenExp;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    // getters and setters
}
