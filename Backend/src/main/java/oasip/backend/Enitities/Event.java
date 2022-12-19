package oasip.backend.Enitities;

import lombok.*;
import oasip.backend.Exception.Event.EventNonOverlab;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@EventNonOverlab(message = "Requested event overlapped with existing events;")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId", nullable = false)
    private Integer id;

    @Column(name = "bookingName", nullable = false, length = 100)
    @NotNull(message = "BookingName must be not null")
    @Size(min = 1, max = 100, message = "BookingName size must be between 1 and 100")
    private String bookingName;

    @Column(name = "bookingEmail", nullable = false)
    @NotNull(message = "BookingEmail must be not null")
    @Size(min = 1, max = 100, message = "BookingEmail size must be between 1 and 100")
    @Email(message = "Email must be a well-formed email address",
            regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
                    "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\" +
                    "\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[" +
                    "a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0" +
                    "-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-" +
                    "z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x" +
                    "7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String bookingEmail;

    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;

    @Column(name = "eventStartTime", nullable = false)
    @NotNull(message = "EventStartTime must be not null")
    @Future(message = "EventStartTime must be a future date/time")
    private Date eventStartTime;

    @Column(name = "eventEndTime", nullable = false)
    private Date eventEndTime;

    @Lob
    @Column(name = "eventNotes")
    @Size(max = 500, message = "EventNotes size must be between 0 and 500")
    private String eventNotes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EventCategory_id", nullable = false)
    @NotNull(message = "EventCategoryId must be not null")
    private Eventcategory eventCategory;

    @Lob
    @Column(name = "eventFile")
    private String eventFile;

    public void setBookingName(String bookingName) {
        if(bookingName != null)
            this.bookingName = bookingName.trim();
    }

    public void setBookingEmail(String bookingEmail) {
        if(bookingEmail != null)
            this.bookingEmail = bookingEmail.trim();
    }

    public void setEventNotes(String eventNotes) {
        if(eventNotes != null)
            this.eventNotes = eventNotes.trim();
    }
}
