package oasip.backend.repositories;

import oasip.backend.Enitities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    User findByEmail(String email);
    List<User> findAllByNameOrEmail(String name , String email);
}

