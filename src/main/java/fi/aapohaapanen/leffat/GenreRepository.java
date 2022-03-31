package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, String> {}
