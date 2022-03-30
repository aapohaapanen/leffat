package fi.aapohaapanen.leffat.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Movie {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private Integer releaseyear;

    @ManyToMany
    private Set<Genre> genres;

    private Integer agelimit;
    private Integer rating;

//    @ManyToMany
//    private Set<Person> actors;

//    @ManyToOne
//    private Person director;

    private String synopsis;
}
