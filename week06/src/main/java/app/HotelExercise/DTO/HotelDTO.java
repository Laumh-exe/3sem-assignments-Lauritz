package app.HotelExercise.DTO;

import app.HotelExercise.Entities.Hotel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HotelDTO {
    private Integer id;
    private String name;   
    private String address;
    private Map<Integer, RoomDTO> rooms;

    public HotelDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.rooms = hotel.getRoomsAsDTO();
    }
}
