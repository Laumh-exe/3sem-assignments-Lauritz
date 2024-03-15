package app.HotelExercise;

import app.HotelExercise.Entities.doesntwork;

import java.util.Set;
public interface ISecurityUser {
    Set<String>  getRolesAsStrings();
    boolean verifyPassword(String pw);
    void addRole(doesntwork role);
    void removeRole(String role);
}