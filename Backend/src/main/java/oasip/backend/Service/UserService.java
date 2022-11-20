package oasip.backend.Service;

import oasip.backend.DTOs.Event.EventDetailDto;
import oasip.backend.DTOs.Event.EventListAllDto;
import oasip.backend.DTOs.User.UserCreateDto;
import oasip.backend.DTOs.User.UserDetailDto;
import oasip.backend.DTOs.User.UserListAllDto;
import oasip.backend.DTOs.User.UserUpdateDto;
import oasip.backend.Enitities.Event;
import oasip.backend.Enitities.User;
import oasip.backend.Enum.Role;
import oasip.backend.Enum.UserRole;
import oasip.backend.Exception.ErrorResponse;
import oasip.backend.ListMapper;
import oasip.backend.repositories.EventRepository;
import oasip.backend.repositories.UserRepository;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

//    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 26, 1, 65536, 10);
    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    @Autowired
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    public ResponseEntity<?> getAllUser() {
        List<User> userList = userRepository.findAll(Sort.by("name").ascending());
        return ResponseEntity.ok(listMapper.maplist(userList, UserListAllDto.class, modelMapper));
    }

    public ResponseEntity<?> getUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException( HttpStatus.NOT_FOUND , userId + " Does not Exist !!!"));
        return ResponseEntity.ok( modelMapper.map(user, UserDetailDto.class));
    }

    public ResponseEntity<?> createUser(UserCreateDto newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = null;
        if(!authentication.getName().contains("anonymousUser")){
            role = (Role) SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0];
        }
        if(authentication.getName().contains("anonymousUser")|| role.getAuthority().contains("admin")){
            User user = modelMapper.map(newUser, User.class);

            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(newUser);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN,"Access denied"));
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException( HttpStatus.NOT_FOUND , userId + " Does not Exist !!!"));
        userRepository.delete(user);
    }

    public ResponseEntity<?> updateUser(UserUpdateDto updateUser, Integer userId) {
        if(updateUser.getRole().length() == 0){
            updateUser.setRole("student");
        }
        User newUser = modelMapper.map(updateUser,User.class);
        for(UserRole r : UserRole.values()){
            if(updateUser.getRole().equals(r.toString()))
                newUser.setRole(r);
        }
        User user = userRepository.findById(userId).map(o -> mapEvent(o, newUser)).orElseGet(() -> {
            newUser.setId(userId);
            return newUser;
        });

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

        userRepository.saveAndFlush(user);
        return ResponseEntity.ok(modelMapper.map(user, UserUpdateDto.class));
    }
    private User mapEvent(User existingUser, User updateUser) {
        if (updateUser.getName() != null) {
            existingUser.setName(updateUser.getName());
        }
        if (updateUser.getEmail() != null) {
            existingUser.setEmail(updateUser.getEmail());
        }
        if (updateUser.getRole() != null) {
            existingUser.setRole(updateUser.getRole());
        }
        return existingUser;
    }
}

