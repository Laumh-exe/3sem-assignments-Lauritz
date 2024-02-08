package Json_tasks.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonAccountDTO {
    private String firstName;
    private String lastName;
    private String birthDate;
    private AddressDTO address;
    private AccountDetailsDTO account;

    @Override
    public String toString() {
        return "PersonAccountDTO: \n"+
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", birthDate='" + birthDate + '\'' +
            ", \naddress: \n" + address +
            ", \naccount: \n" + account;
    }
}
