package ru.road_to_manga.DB.service_models.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.road_to_manga.DB.service_models.source_for_title.SourceForTitle;

import java.util.Optional;

@Repository
public interface HistoryRepo extends JpaRepository<History, Long> {
    Optional<History> findHistoryBySourceForTitleOrderByMaxChapterDesc(SourceForTitle sourceForTitle);
}
