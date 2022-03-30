package fi.aapohaapanen.leffat.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Person {
    @Id
    private UUID id = UUID.randomUUID();

    private String firstName;
    private String lastName;

    @OneToMany
    private Set<Movie> director;

    @ManyToMany
    private Set<Movie> actor;
}
