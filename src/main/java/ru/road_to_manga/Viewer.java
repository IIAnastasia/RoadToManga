package ru.road_to_manga;

import ru.road_to_manga.DB.HistoryCreatorData;

import java.util.ArrayList;

public class Viewer {
    public static void post(ArrayList<HistoryCreatorData> posts) {
        for (var element : posts) {
            System.out.println("https://vk.com/wall" + element.getOwnerID() + "_" + element.getPostID());
        }
    }
}
