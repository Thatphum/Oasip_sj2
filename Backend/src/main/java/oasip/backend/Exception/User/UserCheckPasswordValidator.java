package oasip.backend.Exception.User;

import oasip.backend.BeanUtil;
import oasip.backend.Enum.UserRole;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserCheckPasswordValidator implements ConstraintValidator<UserCheckPassword, String> {

    @Override
    public void initialize(UserCheckPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if( s == null || (s.length() > 7 && s.length() < 15) || s.matches("^\\$argon2id\\$v=(?:16|19)\\$m=\\d{1,10},t=\\d{1,10},p=\\d{1,3}(?:,keyid=[A-Za-z0-9+/]{0,11}(?:,data=[A-Za-z0-9+/]{0,43})?)?\\$[A-Za-z0-9+/]{11,64}\\$[A-Za-z0-9+/]{16,86}$"))
            return true;
        return false;
    }
}
