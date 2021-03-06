package fi.aapohaapanen.leffat.jpa;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.*;

@Entity
@Data
public class Movie {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @Column(name = "release_year")
    private Integer year;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH})
    private Set<Genre> genres;

    private Integer ageLimit;
    private Integer rating;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH})
    private Set<Person> actors;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH})
    private Person director;

    private String synopsis;

    @Override
    public boolean equals(Object other) {
        if (other instanceof Movie o) {
            return Objects.equals(id, o.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public boolean matches(String searchTerm) {
        return StringUtils.containsIgnoreCase(name, searchTerm)
                || StringUtils.containsIgnoreCase(year.toString(), searchTerm)
                || StringUtils.containsIgnoreCase(synopsis, searchTerm)
                || director.matches(searchTerm)
                || genres.stream().anyMatch(g -> g.matches(searchTerm))
                || actors.stream().anyMatch(a -> a.matches(searchTerm));
    }
}
