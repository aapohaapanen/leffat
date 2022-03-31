package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends CrudRepository<Person, UUID> {
    public List<Person> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
}
