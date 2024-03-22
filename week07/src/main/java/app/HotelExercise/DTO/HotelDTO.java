package app.HotelExercise.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.HotelExercise.Entities.Hotel;
import lombok.*;

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

    public static List<HotelDTO> toHotelDTOList(List<Hotel> resultList) {
        List<HotelDTO> hotelDTOs = new ArrayList<>(); // Initialize the list
        for (Hotel hotel : resultList) {
            hotelDTOs.add(new HotelDTO(hotel));
        }
        return hotelDTOs;
    }
}
