package ru.road_to_manga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewInfo {
    private String link;
    private String titleName;
    private List<Integer> chapters;
}
