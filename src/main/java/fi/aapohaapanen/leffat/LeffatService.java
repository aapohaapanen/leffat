package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Genre;
import fi.aapohaapanen.leffat.jpa.Movie;
import fi.aapohaapanen.leffat.jpa.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeffatService {

    @Autowired
    private LeffatRepository leffatRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<Movie> allMovies() {
        var allMovies = leffatRepository.findAll();
        var list = new ArrayList<Movie>();
        allMovies.forEach(list::add);
        return list;
    }

    public List<Movie> addMovies(List<Movie> movies) {
        return movies.stream()
                .map(this::addMovie)
                .toList();
    }
    
    public Movie addMovie(Movie movie) {
        var toPersist = new Movie();
        toPersist.setName(movie.getName());
        toPersist.setYear(movie.getYear());
        toPersist.setSynopsis(movie.getSynopsis());
        toPersist.setRating(movie.getRating());
        toPersist.setYear(movie.getYear());
        toPersist.setAgeLimit(movie.getAgeLimit());

        var genres = movie.getGenres().stream()
                .map(this::findOrCreate)
                .collect(Collectors.toSet());
        toPersist.setGenres(genres);

        var actors = movie.getActors().stream()
                .map(this::findOrCreate)
                .collect(Collectors.toSet());
        toPersist.setActors(actors);

        var director = Optional.ofNullable(movie.getDirector())
                .map(this::findOrCreate)
                .orElse(null);
        toPersist.setDirector(director);

        return leffatRepository.save(toPersist);
    }

    private Genre findOrCreate(Genre in) {
        return genreRepository.findById(in.getName()).orElse(in);
    }

    private Person findOrCreate(Person in) {
        var fromDb = personRepository.findByFirstNameAndLastNameAllIgnoreCase(in.getFirstName(), in.getLastName());
        if (fromDb.isEmpty()) {
            return in;
        } else {
            return fromDb.get(0);
        }
    }
}
