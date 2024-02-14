package API_Tasks.Mappers;

import API_Tasks.DTO.MovieDTO;
import API_Tasks.DTO.VisualMediaDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MovieMapper implements IMediaMapper {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static String apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOGU3M2ViZGYxOTA0MGZhMTM4M2Y4NTIxMjRkYjM4ZCIsInN1YiI6IjY1YzBjMWI4OTAyMDEyMDE3Y2NmMDRhZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9jT94AQ3udraybe3mTrzZsXP-HeaUi6O-0V0USDgEJU";

    @Override
    public String getResponseBody(String url) {
        // Using OkHttp: Can sometime cause program to hang. Then use Apache HttpClient instead.
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url(url)
            .header("Authorization", apiKey)
            .method("GET", null)
            .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = response.body().string();
            System.out.println();
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MovieDTO getByID(String id) {
        String idUrl = "https://api.themoviedb.org/3/find/{id}?external_source=imdb_id"
            .replace("{id}",id);

        String res = getResponseBody(idUrl);
        VisualMediaDTO visualMediaDTO = gson.fromJson(res, VisualMediaDTO.class);
        return visualMediaDTO.getMovie_results()[0];
    }
}
