package unicorns;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "unicorn")
public class Unicorn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unicorn_id", nullable = false)
    private int id;

    @Column(name="name", length = 50)
    private String name;
    private int age;

    @Column(name = "power_strength")
    private int powerStrength;

    public Unicorn() {
    }
}
