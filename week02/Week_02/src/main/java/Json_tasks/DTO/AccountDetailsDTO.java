package Json_tasks.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDetailsDTO {
    private String id;
    private String balance;

    @JsonProperty("isActive")
    private boolean isActive;

    @Override
    public String toString() {
        return
            "id='" + id + '\'' +
            ", balance='" + balance + '\'' +
            ", isActive=" + isActive;
    }
}
