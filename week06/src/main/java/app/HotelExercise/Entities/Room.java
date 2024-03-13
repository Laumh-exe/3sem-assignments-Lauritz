package app.HotelExercise.Entities;

import app.HotelExercise.DTO.RoomDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotelID;

    @Column(name = "number")
    private int number;

    @Column(name = "price")
    private double price;

    public Room(int number, double price, Hotel hotelID) {
        this.number = number;
        this.price = price;
        this.hotelID = hotelID;
    }

    public RoomDTO toDTO() {
        return new RoomDTO(this);
    }
    

}
