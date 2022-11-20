package oasip.backend.repositories;

import oasip.backend.Enitities.EventCategoriesOwner;
import oasip.backend.Enitities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventCategoriesOwnerRepository extends JpaRepository<EventCategoriesOwner, Integer> {

    @Query("select a.eventCategory.id from EventCategoriesOwner a where  a.lecturer.email = :email")
    List<Integer> findAllByLecturer_Email(@Param("email") String email);
}