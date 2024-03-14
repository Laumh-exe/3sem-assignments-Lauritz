package app.HotelExercise.Entities;

import app.HotelExercise.ISecurityUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "users")
public class User implements ISecurityUser {

    @Id
    @Column(name = "username", length = 50, nullable = false, unique = true)
    String username;

    @Column(name = "password")
    String password;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public Set<String> getRolesAsStrings() {
        Set<String> rolesAsStrings = new HashSet<>();
        for (Role role : roles) {
            rolesAsStrings.add(role.getRole());
        }
        return rolesAsStrings;
    }

    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, this.password);
    }

    public void addRole(Role role) {
        if (role != null) {
            if (!roles.contains(role)) {
                roles.add(role);
            }
            if (!role.getUsers().contains(this)) {
                role.addUser(this);
            }
        }

    }

    public void removeRole(String role) {
        for (Role r : roles) {
            if (r.getRole().equals(role)) {
                roles.remove(r);
                break;
            }
        }
    }
}
