package Multithreading.Exercise_6;

import API_Tasks.DTO.VisualMediaDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiMapper {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String getResponse(String url) {
        // Using OkHttp: Can sometime cause program to hang. Then use Apache HttpClient instead.
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url(url)
            .header("Accept","application/json")
            .method("GET", null)
            .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String res = response.body().string();
            System.out.println();
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDadJoke() {
        String res = getResponse("https://icanhazdadjoke.com/");
        JokeDTO joke = gson.fromJson(res, JokeDTO.class);
        return "JOKE: " + joke.joke;
    }

    public static String getChuck() {
        String res = getResponse("https://api.chucknorris.io/jokes/random");
        ChuckDTO chuck = gson.fromJson(res, ChuckDTO.class);
        return "CHUCK: " + chuck.value;
    }

    public static String getKanye() {
        String res = getResponse("https://api.kanye.rest");
        KanyeDTO kanye = gson.fromJson(res, KanyeDTO.class);
        return "KANYE: " + kanye.quote;
    }

    public static String getTrump() {
        String res = getResponse("https://api.whatdoestrumpthink.com/api/v1/quotes/random");
        TrumpDTO trump = gson.fromJson(res, TrumpDTO.class);
        return "TRUMP: " + trump.message;
    }

    public static String getSpaceX() {
        String res = getResponse("https://api.spacexdata.com/v5/launches/latest");
        SpaceXDTO spaceX = gson.fromJson(res, SpaceXDTO.class);
        return "Latest SpaceX launch : " + spaceX.date_local;
    }

    /*
    public static String getRandomFact() {
        String res = getResponse("https://api.spacexdata.com/v5/launches/latest");
        SpaceXDTO spaceX = gson.fromJson(res, SpaceXDTO.class);
        return "Latest SpaceX launch : " + spaceX.date_local;
    }

     */


    @Setter
    @Getter
    public class JokeDTO {
        String joke;
    }

    @Setter
    @Getter
    public class ChuckDTO {
        String value;
    }

    @Setter
    @Getter
    public class KanyeDTO {
        String quote;
    }

    @Setter
    @Getter
    public class TrumpDTO {
        String message;
    }

    @Setter
    @Getter
    public class SpaceXDTO {
        String date_local;
    }

}
