package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeffatRestController {

    @Autowired
    private LeffatService leffat;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/allMovies")
    public List<Movie> allMovies() {
        return leffat.allMovies();
    }

    @PostMapping(path = "/addMovies", consumes = "application/json")
    public List<Movie> addMovies(@RequestBody List<Movie> movies) {
        return leffat.addMovies(movies);
    }

    @PostMapping(path = "/addMovie", consumes = "application/json")
    public Movie addMovie(@RequestBody Movie movie) {
        return leffat.addMovie(movie);
    }
}
