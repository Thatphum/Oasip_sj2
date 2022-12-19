package oasip.backend.Exception.User;

import oasip.backend.BeanUtil;
import oasip.backend.Enitities.User;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameOrEmailNotUniqueValidator implements ConstraintValidator<UserNameOrEmailNotUnique, User> {
    @Autowired
    private UserRepository userRepository;

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(UserNameOrEmailNotUnique constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        userRepository = BeanUtil.getBean(UserRepository.class);
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        Boolean result = true;
        User userName = userRepository.findByName(user.getName());
        System.out.println(userName);
        constraintValidatorContext.disableDefaultConstraintViolation();
        if(userName != null && user.getId() != userName.getId()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("The Name must be unique.").addNode("name").addConstraintViolation();
            result = false;
        }
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail != null && user.getId() != userEmail.getId()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("The Email must be unique.").addNode("email").addConstraintViolation();
            result = false;
        }
        return result;
    }
}
