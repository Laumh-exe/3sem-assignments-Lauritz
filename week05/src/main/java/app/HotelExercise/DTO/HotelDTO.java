package app.HotelExercise.DTO;

import java.util.Map;

import app.HotelExercise.Entities.Hotel;
import lombok.*;

@Getter
@Setter
public class HotelDTO {
    private String id;
    private String name;   
    private String address;
    private Map<String, RoomDTO> rooms;

    public HotelDTO(Hotel hotel) {
        this.id = hotel.getId().toString();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.rooms = hotel.getRooms();
    }
}
