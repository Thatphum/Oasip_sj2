package oasip.backend.DTOs.Authentication;

import lombok.Data;

import java.io.Serializable;
@Data
public class TokenDto implements Serializable {
    private String token;
}
