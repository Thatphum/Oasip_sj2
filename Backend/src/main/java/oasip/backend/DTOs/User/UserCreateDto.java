package oasip.backend.DTOs.User;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserCreateDto implements Serializable {
    private String name;
    private String email;
    private String password;
    private String role;
    public void setRole(String role) {
        this.role = role.length()==0? "student":role;
    }
}
