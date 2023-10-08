package ru.road_to_manga.DB.service_models.indicator_model;

import lombok.Data;
import ru.road_to_manga.enums.SourceEnum;

@Data
public class IndicatorDTO {
    private SourceEnum type;
}
