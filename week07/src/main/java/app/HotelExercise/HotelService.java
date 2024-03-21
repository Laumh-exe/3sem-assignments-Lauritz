package app.HotelExercise;

import java.util.ArrayList;
import java.util.List;

import app.HotelExercise.Entities.Hotel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelService {
    public static List<Hotel> hotels = new ArrayList<Hotel>();
    
    public static void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }
}
