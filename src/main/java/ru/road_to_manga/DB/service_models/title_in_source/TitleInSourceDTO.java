package ru.road_to_manga.DB.service_models.title_in_source;

import lombok.Data;
import ru.road_to_manga.DB.service_models.sorce_content.SourceContentDTO;

@Data
public class TitleInSourceDTO {
    private long userID;
    private long titleID;
    private SourceContentDTO sourceContentDTO;


    private String searchQuery;

    private int lastChapterNumb;


}
