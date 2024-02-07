package Json_tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private String id;
    private String balance;

    @JsonProperty("isActive")
    private boolean isActive;
}
