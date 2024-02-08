package Json_tasks;

import Json_tasks.DTO.PersonAccountDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class AccountMapper {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public String readJson(String path) throws IOException {
        Path filePath = Path.of(path);
        return Files.readString(filePath);
    }

    public List<PersonAccountDTO> getPerson() throws IOException {
        String idUrl = "file:///C:/Users/lauri/Documents/Dev/Java/Intellij/3.SEM/3sem-assignments-template-main/week02/Week_02/src/main/java/Json_tasks/account.json";
        String fileContent = readJson("src/main/java/Json_tasks/account.json");
        List<PersonAccountDTO> personAccountDTOS = Collections.singletonList(gson.fromJson(fileContent, PersonAccountDTO.class));
        return personAccountDTOS;
    }
}
