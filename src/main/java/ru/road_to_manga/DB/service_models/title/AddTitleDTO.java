package ru.road_to_manga.DB.service_models.title;

import lombok.Data;

@Data
public class AddTitleDTO {
    private long userID;
    private String titleName;
    private int allMissedSince;

}
