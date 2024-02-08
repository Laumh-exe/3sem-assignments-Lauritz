package Json_tasks.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimplifiedPersonDTO {



    private String fullName;
    private String city;
    private int zipCode;
    private boolean isActive;

    @JsonCreator
    public SimplifiedPersonDTO(String firstName, String lastName, String city, int zipCode, boolean isActive) {
        this.fullName = firstName + " " + lastName;
        this.city = city;
        this.zipCode = zipCode;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "SimplifiedPersonDTO{" +
            "fullName='" + fullName + '\'' +
            ", city='" + city + '\'' +
            ", zipCode=" + zipCode +
            ", isActive=" + isActive +
            '}';
    }
}
