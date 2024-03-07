package app.HotelExercise.Entities;

import java.util.UUID;

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
}
