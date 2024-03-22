package app.HotelExercise.Entities;
import com.fasterxml.jackson.annotation.JsonBackReference;

import app.HotelExercise.DTO.RoomDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

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

    @JsonBackReference
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
