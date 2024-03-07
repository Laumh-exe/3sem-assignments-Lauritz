package app.HotelExercise.DTO;

import java.util.UUID;

public class RoomDTO {
    private String id;
    private String hotelID;
    private int number;
    private double price;

    public RoomDTO(Room room) {
        this.id = room.getId().toString();
        this.hotelID = room.getHotelID().toString();
        this.number = room.getNumber();
        this.price = room.getPrice();
    }
    
    
}
