package oasip.backend.Enitities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "event_categories_owner")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoriesOwner {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eventCategoryId", nullable = false)
    private Eventcategory eventCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecturerId", nullable = false)
    private User lecturer;

}