package app.HotelExercise.Entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToMany
    @JoinColumn(name = "role_id")
    Set<User> users = new HashSet<>();

    String role;

    public Role(String role) {
        this.role = role;
    }

    public void addUser(User user) {
        if(user != null) {
            if(!users.contains(user)) {
                users.add(user);
            }
            if(!user.getRoles().contains(this)) {
                user.addRole(this);
            }
        }
    }
}
