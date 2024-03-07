package app.HotelExercise.Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import app.HotelExercise.DTO.RoomDTO;
import lombok.*;

@Getter
@Setter
public class Hotel {
    private UUID id;
    private String name;
    private String address;
    private Map<String, Room> rooms;

    public Hotel(String name, String address, Map<String, Room> rooms) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.rooms = rooms;
    }
    
    public Map<String , RoomDTO> getRoomsAsDTO(){
        Map<String, RoomDTO> roomsDTO = new HashMap<String, RoomDTO>();
        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            roomsDTO.put(entry.getKey(), new RoomDTO(entry.getValue()));
        }
        return roomsDTO;
    }
}
