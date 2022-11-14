package oasip.backend.Exception.User;

import oasip.backend.Enitities.User;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserUniqueEmailValidator implements ConstraintValidator<UserUniqueEmail, String> {
    @Autowired
    UserRepository userRepository;

    private String id;
    @Override
    public void initialize(UserUniqueEmail constraintAnnotation) {
        this.id = constraintAnnotation.fieldId();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(this.id.equals("0")){
//            System.out.println(id);
            User result = userRepository.findByEmail(s);
            return result == null;
        }else {

//            List<User> allUser = userRepository.findAll();
//
//            User oldUser = userRepository.findById(this.id).orElseThrow(
//                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, this.id + " Does not Exist !!!"));
//
//            List<User> result = allUser.stream().filter(
//                    v -> v.getId() != oldUser.getId()).collect(Collectors.toList());
//
//            List<User> checkUnique = result.stream().filter(v -> {
//                return v.getEmail().toLowerCase().equals(s.toLowerCase());
//            }).collect(Collectors.toList());
//            if(checkUnique.size() > 0){
//                return false;
//            }

            return true;
        }

    }

//    @Override
//    public boolean isValid(UserUpdateDto userUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
//        List<User> allUser = userRepository.findAll();
//        User oldUser = userRepository.findById(userUpdateDto.getId()).orElseThrow(
//                () -> new ResponseStatusException( HttpStatus.NOT_FOUND , userUpdateDto.getId() + " Does not Exist !!!"));
//        List<User> result = allUser.stream().filter(
//                v -> v.getId() != oldUser.getId()).collect(Collectors.toList());
//        List<User> checkUnique = result.stream().filter(v -> {
//            if(v.getName().toLowerCase().equals(userUpdateDto.getName().toLowerCase()) || v.getEmail().toLowerCase().equals(userUpdateDto.getEmail().toLowerCase())){
//                return true;
//            }
//            return false;
//        }).collect(Collectors.toList());
//        System.out.println("dsakldjlksja: "+ checkUnique.size());
//        if(checkUnique.size() > 0){
//            return false;
//        }
//        return true;
//    }

//    @Override
//    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//        System.out.println(id);
//        User result = userRepository.findByEmail(s);
//        if(result == null)return true;
//        return false;
//    }
}
