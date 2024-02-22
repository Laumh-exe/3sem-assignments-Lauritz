package exercise1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    // Relationer 1:1

    @OneToOne(mappedBy="person", cascade = CascadeType.ALL)
    private PersonDetail personDetail;

    public Person(String name)
    {
        this.name = name;
    }

    // Relationer 1:m

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<Fee> fees = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();

    // Bi-directional update


    public void addPersonDetail(PersonDetail personDetail)
    {
        this.personDetail = personDetail;
        if (personDetail != null)
        {
            personDetail.setPerson(this);
        }
    }

    public void addFee(Fee fee)
    {
        this.fees.add(fee);
        if (fee != null)
        {
            fee.setPerson(this);
        }
    }

    public void addNote(Note note) {
        this.notes.add(note);
        if (note != null) {
            note.setCreatedBy(this);
        }
    }

}
