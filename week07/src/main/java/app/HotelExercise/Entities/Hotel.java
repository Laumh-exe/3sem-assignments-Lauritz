package app.HotelExercise.Entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import app.HotelExercise.DTO.RoomDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel") 
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "hotelID", cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Set<Room> rooms = new HashSet<>();

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
    }
    
    public Map<Integer , RoomDTO> getRoomsAsDTO(){
        Map<Integer, RoomDTO> roomsDTO = new HashMap<>();
        for (Room room : rooms) {
            roomsDTO.put(room.getId(), room.toDTO());
        }
        return roomsDTO;
    }

    public void addRoom(Room room) {
        if(!rooms.contains(room)) {
            rooms.add(room);
            if(room.getHotelID() != this) {
                room.setHotelID(this);
            }
        }
    }
}
