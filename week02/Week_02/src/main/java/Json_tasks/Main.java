package Json_tasks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    //PART 1
    /*
    - What does JSON stand for?
    JavaScript Object Notation

    -What is the difference between JSON and XML?
    Json is easier to read and does not use tags. Smaller and can transfer data faster. Json kan also use arrays

    -For what is JSON generally used for?
    Json stores data and transports data in a text format and translates into JavaScript objects

    -Write down the 6 data types in JSON.
    a string
    a number
    an object (JSON object)
    an array
    a boolean
    null

    -Write down the 4 JSON syntax rules.
    Data is in name/value pairs
    Data is separated by commas
    Curly braces hold objects
    Square brackets hold arrays
     */

    //PART 3


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Path.of("src/main/java/Json_tasks/account.json");
        String jsonString = Files.readString(filePath);
        List<Person> people = objectMapper.readValue(jsonString, new TypeReference<List<Person>>() {});

        System.out.println(people.get(0).getFirstName());
    }

}
