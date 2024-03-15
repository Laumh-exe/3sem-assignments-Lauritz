package app.HotelExercise.DTO;

import app.HotelExercise.Entities.doesntwork;
import app.HotelExercise.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    public UserDTO(User created) {
        //TODO Auto-generated constructor stub
    }

    private String username;
    private String password;

    Set<doesntwork> roles = new HashSet<>();
}
