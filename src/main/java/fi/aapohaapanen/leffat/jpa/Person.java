package fi.aapohaapanen.leffat.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
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
    @JsonIgnore
    private Set<Movie> director;

    @ManyToMany
    @JsonIgnore
    private Set<Movie> actor;

    @Override
    public boolean equals(Object other) {
        if (other instanceof Person o) {
            return Objects.equals(id, o.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
