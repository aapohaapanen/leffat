package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Genre;
import fi.aapohaapanen.leffat.jpa.Movie;
import fi.aapohaapanen.leffat.jpa.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JpaTest {

    @Autowired
    private LeffatRepository repository;

    @Test
    public void emptyRepo() {
        var result = repository.findAll().iterator();
        assertFalse(result.hasNext());
    }

    @Test
    public void saveMovie() {
        var movie = new Movie();
        movie.setName("Moviename");
        movie.setGenres(Set.of(genre("Action"), genre("Comedy"), genre("Animation")));
        movie.setRating(4);
        movie.setAgeLimit(12);
        movie.setSynopsis("Lorem ipsum");
        movie.setYear(1995);
        movie.setDirector(person("George", "Lucas"));
        movie.setActors(Set.of(person("Jack", "Black"), person("Uma", "Thurman")));

        repository.save(movie);

        var allMovies = repository.findAll().iterator();
        assertTrue(allMovies.hasNext());
        var movieFromRepo = allMovies.next();
        assertFalse(allMovies.hasNext());

        assertEquals("Moviename", movieFromRepo.getName());
        assertEquals(4, movieFromRepo.getRating());
        assertEquals(12, movieFromRepo.getAgeLimit());
        assertEquals(1995, movieFromRepo.getYear());
        assertEquals("Lorem ipsum", movieFromRepo.getSynopsis());
        assertEquals("George", movieFromRepo.getDirector().getFirstName());
        assertEquals("Lucas", movieFromRepo.getDirector().getLastName());

        var genres = movieFromRepo.getGenres().stream().map(Genre::getName).collect(Collectors.toSet());
        assertEquals(Set.of("Action", "Comedy", "Animation"), genres);

        var actors = movieFromRepo.getActors().stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .collect(Collectors.toSet());
        assertEquals(Set.of("Jack Black", "Uma Thurman"), actors);
    }

    private Genre genre(String name) {
        return new Genre(name);
    }

    private Person person(String firstName, String lastName) {
        var person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }
}
