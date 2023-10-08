package ru.road_to_manga.DB.service_models.source_for_title;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.road_to_manga.DB.service_models.title_in_source.TitleInSource;

@Repository
public interface SourceForTitleRepo extends JpaRepository<
        SourceForTitle, Long> {
    SourceForTitle getSourceForTitleByTitleInSource(
            TitleInSource titleInSource);
}
