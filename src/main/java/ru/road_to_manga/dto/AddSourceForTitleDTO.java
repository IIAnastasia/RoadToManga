package ru.road_to_manga.dto;

import lombok.Data;
import ru.road_to_manga.DB.service_models.indicator_model.IndicatorDTO;
import ru.road_to_manga.DB.service_models.sorce_content.SourceContentDTO;
import ru.road_to_manga.DB.service_models.source.SourceDTO;
import ru.road_to_manga.DB.service_models.title.AddTitleDTO;

import java.util.List;

@Data
public class AddSourceForTitleDTO {
    private long userId;
    private AddTitleDTO titleDTO;
    private SourceDTO sourceDTO;
    private IndicatorDTO indicatorDTO;
    private SourceContentDTO contentDTO;
    private List<Integer> chapters;
}
