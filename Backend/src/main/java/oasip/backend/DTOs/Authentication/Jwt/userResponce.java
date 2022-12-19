package oasip.backend.DTOs.Authentication.Jwt;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class userResponce {
    private String email;
    private String username;
    private String roles;

    public userResponce(String email, String username, String roles) {
        this.email = email;
        this.username = username;
        this.roles = roles;
    }
}
