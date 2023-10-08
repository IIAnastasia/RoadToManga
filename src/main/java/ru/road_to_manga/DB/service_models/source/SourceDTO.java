package ru.road_to_manga.DB.service_models.source;

import lombok.Data;
import ru.road_to_manga.enums.SourceEnum;

@Data
public class SourceDTO {
    //    private long id = 100;
    private long userId;
    private SourceEnum type;
    private boolean isBlocked;
    private String name;
}
