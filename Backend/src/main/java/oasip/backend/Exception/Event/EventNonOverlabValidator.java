package oasip.backend.Exception.Event;

import oasip.backend.BeanUtil;
import oasip.backend.Enitities.Event;
import oasip.backend.Enitities.Eventcategory;
import oasip.backend.repositories.CategoryRepository;
import oasip.backend.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;
import java.util.List;

public class EventNonOverlabValidator implements ConstraintValidator<EventNonOverlab, Event> {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EventRepository eventRepository;

    @Override
    public void initialize(EventNonOverlab constraintAnnotation) {
        categoryRepository = BeanUtil.getBean(CategoryRepository.class);
        eventRepository = BeanUtil.getBean(EventRepository.class);
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext constraintValidatorContext) {
        if(event.getEventCategory() != null) {
            Eventcategory eventcategory = categoryRepository.getById(event.getEventCategory().getId());
            event.setEventDuration(eventcategory.getEventCategoryDuration());
            if(event.getEventDuration() == null ) event.setEventDuration(eventcategory.getEventCategoryDuration());
            Date newEndTime =  new Date(event.getEventStartTime().getTime() + (event.getEventDuration() * 60000));
            event.setEventEndTime(newEndTime);
            // split between create event and update event by id
            // create don't have id
            // update event have id
            Integer id = event.getId() != null ? event.getId() : 0;
            List<Event> eventList = eventRepository.findAllByOverlab(event.getEventStartTime() , newEndTime , id);
            if(!eventList.isEmpty()){
                // disable exception because cannot set field name so create new exception and field name
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()).addNode("isOverlab").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
