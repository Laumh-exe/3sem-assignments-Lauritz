package app.HotelExercise.DTO;

import app.HotelExercise.Entities.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RoomDTO {
    private Integer id;
    private String hotelID;
    private int number;
    private double price;

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.hotelID = room.getHotelID().toString();
        this.number = room.getNumber();
        this.price = room.getPrice();
    }
}
