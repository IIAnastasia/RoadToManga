package ru.road_to_manga.DB.service_models.sorce_content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.road_to_manga.DB.service_models.source.Source;

@Repository
public interface SourceContentRepo extends JpaRepository<Source, Long> {
}
