package ru.road_to_manga.DB.service_models.title_in_source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.road_to_manga.DB.service_models.source.Source;

import java.util.List;

@Repository
public interface TitleInSourceRepo extends JpaRepository<
        TitleInSource, Long> {

    List<TitleInSource> findBySource(Source source);
}
