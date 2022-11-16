package oasip.backend.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oasip.backend.Config.Jwts.JwtUserDetailsService;
import oasip.backend.DTOs.Event.EventCreateDto;
import oasip.backend.DTOs.Event.EventDetailDto;
import oasip.backend.DTOs.Event.EventEditDto;
import oasip.backend.DTOs.Event.EventListAllDto;
import oasip.backend.Enitities.Event;
import oasip.backend.Enitities.Eventcategory;
import oasip.backend.Enum.Role;
import oasip.backend.Exception.ErrorResponse;
import oasip.backend.ListMapper;
import oasip.backend.Service.Email.EmailService;
import oasip.backend.repositories.CategoryRepository;
import oasip.backend.repositories.EventCategoriesOwnerRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    JwtUserDetailsService jwtUserDetailService;
    @Autowired
    private EventCategoriesOwnerRepository eventCategoriesOwnerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    public ResponseEntity<?> getAllEvent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = (Role) SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0];
        List<Event> events;
        if(role.getAuthority().contains("admin")) { // admin
            events = eventRepository.findAll(Sort.by("eventStartTime").descending());
        }else if(role.getAuthority().contains("student")){ // student
            events = eventRepository.findByBookingEmail(authentication.getName(),Sort.by("eventStartTime").descending());
        }else { // lecturer
            List<Integer> eventCategoriesOwner = eventCategoriesOwnerRepository.findAllByLecturer_Email(authentication.getName());
            events = eventRepository.findAllByEventCategory_IdList(eventCategoriesOwner,Sort.by("eventStartTime").descending());
        }
        return ResponseEntity.ok(listMapper.maplist(events, EventListAllDto.class, modelMapper));
    }

    public ResponseEntity<?> getEvent(Integer eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = (Role) SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0];
        List<Integer> eventCategoriesOwner = null;
        if(role.getAuthority().contains("lecturer")){ // lecturer
            eventCategoriesOwner = eventCategoriesOwnerRepository.findAllByLecturer_Email(authentication.getName()); // email
        }
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResponseStatusException( HttpStatus.NOT_FOUND , eventId + " Does not Exist !!!"));
        if(role.getAuthority().contains("admin") || authentication.getName().contains(event.getBookingEmail()) || eventCategoriesOwner.contains(event.getEventCategory().getId())){
            return ResponseEntity.ok(modelMapper.map(event, EventDetailDto.class));
        }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN,"Access denied"));
    }

    public List<EventListAllDto> filterEvents(Integer categoryId , Integer optionId , Date time) {
        if (categoryId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "categoryId is not null");
        }
        List<Event> events = null;
        if (categoryId != 0) {
            events = eventRepository.findByEventCategory_Id(categoryId, Sort.by("eventStartTime").descending());
        } else {
            events = eventRepository.findAll(Sort.by("eventStartTime").descending());
        }
        List<EventListAllDto> newEvent = listMapper.maplist(events, EventListAllDto.class, modelMapper);
        List<EventListAllDto> filteroption = null;
        switch (optionId) {
            case 1: //Upcoming
                filteroption = getUpcoming(newEvent);
                break;
            case 2: //Past
                filteroption = getPast(newEvent);
                break;
            case 3: // Start Time
                filteroption = getDay(newEvent, time);
                break;
            default:
                filteroption = newEvent;
                break;
        }
        return filteroption;
    }

    public List<EventListAllDto> getEachEventCategories(Integer categoryId) {
        List<Event> events = eventRepository.findByEventCategory_Id(categoryId, Sort.by("eventStartTime").descending());
        return listMapper.maplist(events, EventListAllDto.class, modelMapper);
    }

    public List<EventListAllDto> getUpcoming(List<EventListAllDto> event) {
        List<EventListAllDto> eventDtos = listMapper.maplist(event, EventListAllDto.class, modelMapper);
        Date date = new Date();
        List<EventListAllDto> events = eventDtos.stream().filter((value) -> {
            Date valueEnd = new Date();
            valueEnd.setTime((value.getEventStartTime().getTime() + (value.getEventDuration() * 60000)));
            return ((value.getEventStartTime().compareTo(date) > 0) || (value.getEventStartTime().compareTo(valueEnd) > 0));
        }).collect(Collectors.toList());
        Collections.reverse(events);
        return listMapper.maplist(events, EventListAllDto.class, modelMapper);
    }

    public List<EventListAllDto> getPast(List<EventListAllDto> event) {
        Date date = new Date();
        List<EventListAllDto> events = event.stream().filter((value) -> {
            return (value.getEventStartTime().compareTo(date) < 0);
        }).collect(Collectors.toList());
        return listMapper.maplist(events, EventListAllDto.class, modelMapper);
    }

    public List<EventListAllDto> getDay(List<EventListAllDto> event, Date selectday) {
        if (selectday == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is not null ");
        }
        Date date = new Date();
        date.setTime(selectday.getTime());
        Date end = new Date();
        end.setTime(date.getTime() + 86400000);
        List<EventListAllDto> events = event.stream().filter((value) -> {
            return ((value.getEventStartTime().compareTo(date) > 0) && (value.getEventStartTime().compareTo(end) < 0));
        }).collect(Collectors.toList());
        Collections.reverse(events);
        return listMapper.maplist(events, EventListAllDto.class, modelMapper);
    }

    public ResponseEntity<?> createEvent(String jsonEvent) throws JsonProcessingException {
        // convert String Json to obj
        EventCreateDto newEvent = objectMapper.readValue(jsonEvent, EventCreateDto.class);
        Event event = modelMapper.map(newEvent,Event.class);
        // check validation
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = null;
        //if user is anonymous set role to "anonymousUser".
        if(!authentication.getName().contains("anonymousUser")){
            role = (Role) authentication.getAuthorities().toArray()[0];
        }
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName()); // anonymousUser
        if(authentication.getName().contains("anonymousUser") || authentication.getName().contains(event.getBookingEmail()) || role.getAuthority().contains("admin")){
            eventRepository.saveAndFlush(event);
            Eventcategory eventcategory = categoryRepository.getById(event.getEventCategory().getId());
            Date endDate = new Date(event.getEventStartTime().getTime() + (event.getEventDuration() * 60000));
            // send Email
            String subject = "[OASIP] " + eventcategory.getEventCategoryName() + " @ " + event.getEventStartTime() + " - " +  endDate;
            String body = "Booking : " + event.getBookingEmail() + "\n" +
                    "Event Category: " + eventcategory.getEventCategoryName() + "\n" +
                    "When: " + event.getEventStartTime() + "\n" +
                    "Event Notes: " + ((event.getEventNotes() != null) ? event.getEventNotes():" ");
//            System.out.println(subject);
//            System.out.println(body);
//            String status = emailService.sendSimpleMail(event.getBookingEmail() , body , subject);
        }else{
            if (role.getAuthority().contains("lecturer"))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN,"Access denied"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"The booking email must be the same as the student's email"));
        }
        return ResponseEntity.ok(modelMapper.map(event,EventCreateDto.class));
    }

    public void deleteEvent(Integer eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = (Role) SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0];
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResponseStatusException( HttpStatus.NOT_FOUND , eventId + " Does not Exist !!!"));
        if(role.getAuthority().contains("admin") || authentication.getName().contains(event.getBookingEmail())){
            eventRepository.deleteById(eventId);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied");
        }
    }

    public ResponseEntity<?> updateEvent(EventEditDto updateEvent, Integer eventId)  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = (Role) SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0];
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResponseStatusException( HttpStatus.NOT_FOUND , eventId + " Does not Exist !!!"));
        if(role.getAuthority().contains("admin") || authentication.getName().contains(event.getBookingEmail())){
            Event newEdit = modelMapper.map(updateEvent,Event.class);
            mapEvent(event , newEdit);
            // check validation
            Set<ConstraintViolation<Event>> violations = validator.validate(event);
            if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
            eventRepository.saveAndFlush(event);
            return ResponseEntity.ok(modelMapper.map(event, EventEditDto.class));
        }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN,"Access denied"));
    }

    private Event mapEvent(Event existingEvent, Event updateEvent) {
        if (updateEvent.getEventStartTime() != null) {
            existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        }
        if (updateEvent.getEventNotes() != null) {
            existingEvent.setEventNotes(updateEvent.getEventNotes());
        }
        return existingEvent;
    }

}
