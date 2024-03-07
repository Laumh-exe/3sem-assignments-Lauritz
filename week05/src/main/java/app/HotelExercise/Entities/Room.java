package app.HotelExercise.Entities;

import java.util.UUID;

import app.HotelExercise.DTO.RoomDTO;
import lombok.*;

@Getter
@Setter
public class Room {
    private UUID id;
    private String hotelID;
    private int number;
    private double price;

    public Room(String hotelID, int number, double price) {
        this.id = UUID.randomUUID();
        this.hotelID = hotelID;
        this.number = number;
        this.price = price;
    }

    public RoomDTO toDTO() {
        return new RoomDTO(this);
    }
}
