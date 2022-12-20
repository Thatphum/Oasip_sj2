package oasip.backend.DTOs.Event;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EventEditDto implements Serializable {
    private String eventStartTime;
    private String eventNotes;
    private String eventFile;
}
