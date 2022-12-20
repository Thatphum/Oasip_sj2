package oasip.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oasip.backend.DTOs.Event.EventCreateDto;
import oasip.backend.DTOs.Event.EventDetailDto;
import oasip.backend.DTOs.Event.EventEditDto;
import oasip.backend.DTOs.Event.EventListAllDto;
import oasip.backend.Service.EventService;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

//@CrossOrigin
@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping("")
    public ResponseEntity<?> getAllEvents() { return service.getAllEvent(); }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventDetail(@PathVariable Integer eventId) {
        return service.getEvent(eventId);
    }

    @GetMapping("/filter/")
    public List<EventListAllDto> filterEvents(@RequestParam(name = "categoryId") Integer id,
                                              @RequestParam(name = "option", defaultValue = "0" , required = false) Integer optionId ,
                                              @RequestParam(name = "time", defaultValue = "2022-10-02" , required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date time){
        return service.filterEvents(id , optionId , time);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createEvent(@RequestParam(name = "event") String jsonEvent , @RequestParam(name = "file" , required = false) MultipartFile file) throws JsonProcessingException{
        return service.createEvent(jsonEvent ,file);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Integer eventId) throws IOException {
        service.deleteEvent(eventId);
    }

    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateEvent(@RequestParam(name = "event") String jsonEvent,@RequestParam(name = "eventId") Integer eventId , @RequestParam(name = "file" , required = false) MultipartFile file) throws IOException {
        return service.updateEvent(jsonEvent, eventId ,file);
    }
//    @PatchMapping("/{eventId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<?> updateEvent(@PathVariable Integer eventId, @RequestBody EventEditDto updateEvent) {
//        return service.updateEvent(updateEvent, eventId);
//    }
}
