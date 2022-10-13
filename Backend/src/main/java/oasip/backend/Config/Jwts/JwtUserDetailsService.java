package oasip.backend.Config.Jwts;


import oasip.backend.Enitities.User;
import oasip.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

//    @Autowired
//    CustomerRepository customerRepository;
//    @Autowired
//    ReceptionistRepository receptionistRepository;
    @Autowired
    private UserRepository userRepository;

    private Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();

    @Override
    public AuthenticationUser loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
//        Customer customer = customerRepository.findCustomerByEmail(s).orElse(null);
//        Receptionist receptionist = receptionistRepository.findReceptionistByEmail(s).orElse(null);

        if (user != null){
            return  new AuthenticationUser(user.getEmail() , argon2PasswordEncoder.encode(user.getPassword()), user.getRole());
        }
        throw new UsernameNotFoundException("User not found with Email: " + s);
    }
}
