package ru.road_to_manga.service;

import org.springframework.stereotype.Service;
import ru.road_to_manga.DB.models.i_will_delete_one_day.HistoryCreatorData;
import ru.road_to_manga.dto.ViewInfo;

import java.util.ArrayList;

@Service
public class Viewer {
    public static void post(ArrayList<HistoryCreatorData> posts) {
        for (var element : posts) {
            System.out.println("https://vk.com/wall" + element.getOwnerID() + "_" + element.getPostID());
        }
    }

    public void post(ViewInfo viewInfo) {
        if (viewInfo.getTitleName() != null) {
            System.out.println(viewInfo.getTitleName());
        }
        System.out.println(viewInfo.getChapters());
        System.out.println(viewInfo.getLink());
        System.out.println("\n------------------------------\n\n");
    }
}
