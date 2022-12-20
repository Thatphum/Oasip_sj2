package oasip.backend.DTOs.Event;

import lombok.Data;
import oasip.backend.Exception.Event.EventNonOverlab;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class EventCreateDto implements Serializable {
    private  Integer id;
    private String bookingName;
    private String bookingEmail;
    private Date eventStartTime;
    private Integer eventDuration;
    private String eventNotes;
    private Integer eventCategoryId;
    private String eventFile;

}
