//package ru.road_to_manga.service;
//
//import com.vk.api.sdk.client.VkApiClient;
//import com.vk.api.sdk.client.actors.UserActor;
//import com.vk.api.sdk.exceptions.ApiException;
//import com.vk.api.sdk.exceptions.ClientException;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.HistoryCreatorData;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.MainDB;
//
//import java.lang.Math;
//import java.util.HashSet;
//
//public class Editor {
//    MainDB db;
//    VkApiClient vk;
//    UserActor actor;
//    Editor(VkApiClient vk, UserActor actor, MainDB db) {
//        this.vk = vk;
//        this.actor = actor;
//        this.db = db;
//    }
//    void editChapter(int historyID, int startChapter, int endChapter) throws ClientException, ApiException {
//        HistoryCreatorData data = db.getHistoryData(historyID);
//        HashSet<Integer> chaptersToFind = new HashSet<>();
//        for (int chapter = data.getChapterStart(); chapter <
//                Math.min(data.getChapterEnd(), startChapter); ++chapter) {
//            chaptersToFind.add(chapter);
//        }
//        for (int chapter = Math.max(endChapter, data.getChapterStart());
//             chapter < data.getChapterEnd(); ++chapter) {
//            chaptersToFind.add(chapter);
//        }
//        data.setChapterStart(startChapter);
//        data.setChapterEnd(endChapter);
//        db.updateChapter(historyID, data);
//        Searcher searcher = new Searcher(db,vk,actor);
//        searcher.Search(data.getTitle(), chaptersToFind, data);
//    }
//}
