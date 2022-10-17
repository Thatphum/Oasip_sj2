package oasip.backend.repositories;

import oasip.backend.Enitities.EventCategoriesOwner;
import oasip.backend.Enitities.Eventcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Eventcategory,Integer> {
    Eventcategory findByEventCategoryName(String name);

    List<Eventcategory> findByEventCategoriesOwners(EventCategoriesOwner eventCategoriesOwners);
}