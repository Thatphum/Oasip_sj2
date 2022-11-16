package oasip.backend.Enitities;

import lombok.*;
import oasip.backend.Exception.Category.CategoryUniqueName;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "eventcategories")
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@CategoryUniqueName( message = "The eventCategoryName is NOT unique.")
public class Eventcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventCategoryId", nullable = false)
    private Integer id;

    @Column(name = "eventCategoryName", nullable = false, length = 100)
    @NotEmpty(message = "EventCategoryName must be not empty")
    @NotNull(message = "EventCategoryName must be not null")
    @Size(min = 1, max = 100, message = "EventCategoryName size must be between 1 and 100")
    private String eventCategoryName;

    @Lob
    @Column(name = "eventCategoryDescription")
    @Size(max = 500, message = "EventCategoryDescription size must be between 0 and 500")
    private String eventCategoryDescription;

    @Column(name = "eventCategoryDuration", nullable = false)
    @NotNull(message = "Duration must be not null")
    @Max(value = 480 ,message = "Duration must be between 1 and 480.")
    @Min(value =  1 , message = "Duration must be between 1 and 480.")
    private Integer eventCategoryDuration;

    @OneToMany(mappedBy = "eventCategory")
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "eventCategory")
    private Set<EventCategoriesOwner> eventCategoriesOwners = new LinkedHashSet<>();

    public void setEventCategoryName(String eventCategoryName) {
        if(eventCategoryName != null)
            this.eventCategoryName = eventCategoryName.trim();
    }

    public void setEventCategoryDescription(String eventCategoryDescription) {
        if(eventCategoryDescription != null)
            this.eventCategoryDescription = eventCategoryDescription.trim();
    }
}