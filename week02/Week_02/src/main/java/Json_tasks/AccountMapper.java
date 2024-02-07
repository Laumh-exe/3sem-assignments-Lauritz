package Json_tasks;

import API_Tasks.DTO.MovieDTO;
import API_Tasks.DTO.VisualMediaDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public List<Person> getPerson() throws IOException {
        String idUrl = "file:///C:/Users/lauri/Documents/Dev/Java/Intellij/3.SEM/3sem-assignments-template-main/week02/Week_02/src/main/java/Json_tasks/account.json";
        String fileContent = readJson("src/main/java/Json_tasks/account.json");
        List<Person> person = Collections.singletonList(gson.fromJson(fileContent, Person.class));
        return person;
    }
}
