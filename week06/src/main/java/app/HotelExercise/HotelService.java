package app.HotelExercise;

import app.HotelExercise.Entities.Hotel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HotelService {
    public static List<Hotel> hotels = new ArrayList<Hotel>();
    
    public static void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }
}
