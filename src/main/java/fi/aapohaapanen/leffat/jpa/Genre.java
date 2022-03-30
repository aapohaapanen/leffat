package fi.aapohaapanen.leffat.jpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Data
public class Genre {
    @Id
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies;
}
