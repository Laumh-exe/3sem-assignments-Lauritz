package Json_tasks.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String street;
    private String city;
    private int zipCode;

    @Override
    public String toString() {
        return
            "street='" + street + '\'' +
            ", city='" + city + '\'' +
            ", zipCode=" + zipCode;
    }
}
