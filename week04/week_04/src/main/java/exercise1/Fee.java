package exercise1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id")
    private Integer Id;
    private int amount;
    private LocalDate payDate;

    @ToString.Exclude
    @ManyToOne
    private Person person;

    public Fee(int amount, LocalDate payDate) {
        this.amount = amount;
        this.payDate = payDate;
    }
}
