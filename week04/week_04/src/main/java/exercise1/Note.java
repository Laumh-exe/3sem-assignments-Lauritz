package exercise1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="note_id")
    private Integer Id;

    @Column(name = "note")
    private String note;

    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "person_id")
    private Person createdBy;

    @PrePersist
    public void setCreated(){
        this.created = LocalDateTime.now();
    }

    public Note(String note) {
        this.note = note;
    }
}
