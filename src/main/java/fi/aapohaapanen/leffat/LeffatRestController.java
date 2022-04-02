package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/search/{term}")
    public List<Movie> search(@PathVariable String term) {
        return leffat.search(term);
    }

    @PostMapping(path = "/addMovies", consumes = "application/json")
    public List<Movie> addMovies(@RequestBody List<Movie> movies) {
        return leffat.addMovies(movies);
    }

    @PostMapping(path = "/addMovie", consumes = "application/json")
    public Movie addMovie(@RequestBody Movie movie) {
        return leffat.addMovie(movie);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable UUID id) {
        try {
            leffat.deleteMovie(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id " + id + " not found.");
        }
    }
}
