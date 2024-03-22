package app.HotelExercise.DTO;

import java.util.ArrayList;
import java.util.List;

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

    public static List<RoomDTO> toRoomDTOList(List<Room> resultList) {
        List<RoomDTO> roomDTOs = new ArrayList<>(); // Initialize the list
        for (Room room : resultList) {
            roomDTOs.add(new RoomDTO(room));
        }
        return roomDTOs;
    }
}
