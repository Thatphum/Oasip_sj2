package oasip.backend.Exception.User;

import oasip.backend.BeanUtil;
import oasip.backend.Enum.UserRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserCheckRoleValidator implements ConstraintValidator<UserCheckRole, UserRole> {


    @Override
    public void initialize(UserCheckRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRole role, ConstraintValidatorContext constraintValidatorContext) {
        if(role != null){
            for(UserRole r : UserRole.values()){
                if(role.equals(r))
                    return true;
            }
            return false;
        }else{
            return true;
        }
    }
}
