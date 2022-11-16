package oasip.backend.Enitities;

import lombok.*;
import oasip.backend.Enum.UserRole;
import oasip.backend.Exception.User.UserCheckPassword;
import oasip.backend.Exception.User.UserCheckRole;
import oasip.backend.Exception.User.UserNameOrEmailNotUnique;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@UserNameOrEmailNotUnique(field = "id", fieldMatch = "email", message = "The Email or Name must be unique.")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    @NotNull(message = "Name must be not null.")
    @NotEmpty(message = "Name must be not Empty.")
    @Size(min = 1, max = 100, message = "name size must be between 1 and 100.")
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    @NotNull(message = "Email must be not null.")
    @NotEmpty(message = "Email must be not Empty.")
    @Size(min = 1, max = 50, message = "Email size must be between 1 and 50.")
    @Email(message = "Email must be a well-formed email address",
            regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
                    "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\" +
                    "\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[" +
                    "a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0" +
                    "-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-" +
                    "z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x" +
                    "7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
    @Column(name = "password", nullable = false, length = 90)
    @NotNull(message = "password must be not null.")
    @UserCheckPassword(message = "Password size must be between 8 and 14.")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @NotNull(message = "The Role must be admin, lecturer, or student.")
    @UserCheckRole(message = "The Role must be admin, lecturer, or student")
    private UserRole role;

    @Column(name = "createOn", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createOn;

    @Column(name = "updateOn", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updateOn;


    @OneToMany(mappedBy = "lecturer")
    private Set<EventCategoriesOwner> eventCategoriesOwners = new LinkedHashSet<>();

}