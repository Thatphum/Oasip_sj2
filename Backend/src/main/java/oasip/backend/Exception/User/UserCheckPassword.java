package oasip.backend.Exception.User;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = UserCheckPasswordValidator.class
)

public @interface UserCheckPassword {
    String message() default "Password size must be between 8 and 14.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
