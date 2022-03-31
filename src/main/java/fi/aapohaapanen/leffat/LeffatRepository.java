package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LeffatRepository extends CrudRepository<Movie, UUID> {
}
