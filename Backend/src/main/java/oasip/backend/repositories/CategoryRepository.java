package oasip.backend.repositories;

import oasip.backend.Enitities.Event;
import oasip.backend.Enitities.EventCategoriesOwner;
import oasip.backend.Enitities.Eventcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Eventcategory,Integer> {
    Eventcategory findByEventCategoryName(String name);

    @Query(value = "select c from Eventcategory c where c.eventCategoryName = :name and c.id NOT IN :id")
    List<Eventcategory> findAllByEventCategoryNameAndAndIdFalse(@Param("name") String name , @Param("id") Integer id);

    List<Eventcategory> findByEventCategoriesOwners(EventCategoriesOwner eventCategoriesOwners);
}