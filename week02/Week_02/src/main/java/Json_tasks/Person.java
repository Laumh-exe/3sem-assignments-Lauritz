package Json_tasks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private String firstName;
    private String lastName;
    private String birthDate;
    private Address address;
    private Account account;
}
