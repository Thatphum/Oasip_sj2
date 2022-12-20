package oasip.backend.repositories;

import oasip.backend.Enitities.Event;
import oasip.backend.Enitities.EventCategoriesOwner;
import oasip.backend.Enitities.Eventcategory;
import oasip.backend.Enitities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByEventCategory_Id(Integer categoryId, Sort sort);

    @Query(value = "select e from Event e where e.eventCategory.id IN :id")
    List<Event> findAllByEventCategory_IdList(@Param("id") List<Integer> id , Sort sort);

//    @Query(value = "select e from Event e where e.eventCategory.id IN :id")
//    List<Event> findAllByEventCategory_IdList(@Param("id") List<Integer> id , Sort sort);

    List<Event> findByBookingEmail(String email, Sort sort);

    List<Event> findByBookingEmail_AndEventCategory_Id(String email , Integer categoryId, Sort sort);

    @Query(value = "select e from Event e where ((e.eventStartTime <= :startTime and :startTime < e.eventEndTime) or (e.eventStartTime < :endTime and :endTime <= e.eventEndTime)) and e.id NOT IN :eventId ")
    List<Event> findAllByOverlab(@Param("startTime") Date startTime , @Param("endTime") Date endTime , @Param("eventId") Integer eventId);
}