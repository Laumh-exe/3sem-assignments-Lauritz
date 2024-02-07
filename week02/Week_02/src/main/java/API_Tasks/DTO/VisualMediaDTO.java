package API_Tasks.DTO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VisualMediaDTO {
    private MovieDTO[] movie_results;
    private PersonDTO[] person_results;
    private TvDTO[] tv_results;
    private TvEpisodeDTO[] tv_episode_results;
    private TvEpisodeDTO[] tv_Season_results;
}
