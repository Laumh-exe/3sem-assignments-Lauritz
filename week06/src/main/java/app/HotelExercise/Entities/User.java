package app.HotelExercise.Entities;

import app.HotelExercise.ISecurityUser;
import app.HotelExercise.BCrypt.BCrypt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "user")
public class User implements ISecurityUser {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;

        @Column(name = "username")
        String username;

        @Column(name = "password")
        String password;
    
        @ManyToMany(mappedBy = "users")
        Set<Role> roles = new HashSet<>();

          public User(String username, String password) {
                this.username = username;
                this.password = BCrypt.hashpw(password, BCrypt.gensalt());
          }        


        public Set<String> getRolesAsStrings() {
            return null;
        }

        public boolean verifyPassword(String pw) {
            return BCrypt.checkpw(pw, this.password);
        }

        public void addRole(Role role) {
            if(role != null) {
                role.addUser(this);
                roles.add(role);
            }
            
        }

        public void removeRole(String role) {
            for(Role r : roles) {
                if(r.getRole().equals(role)) {
                    roles.remove(r);
                    break;
                }
            }
        }
    
}
