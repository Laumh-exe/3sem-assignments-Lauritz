package The_Point_exercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "points")
@NoArgsConstructor
@Getter
@ToString
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    private int id;

    @Column(name = "x")
    private int x;

    @Column(name = "Y")
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
