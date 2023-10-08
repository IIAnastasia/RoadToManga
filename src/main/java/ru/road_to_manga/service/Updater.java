package ru.road_to_manga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.road_to_manga.DB.service_models.source.Source;
import ru.road_to_manga.DB.service_models.source.SourceRepo;
import ru.road_to_manga.DB.vk_models.vk_source.VkSource;
import ru.road_to_manga.enums.SourceEnum;
import ru.road_to_manga.service.update_strategies.simpleVk.SimpleIsolatedVkStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class Updater {
    private final SourceRepo sourceRepo;
    private final SimpleIsolatedVkStrategy vkStrategy;

    public void Update(long userId) {
        Map<SourceEnum, List<Source>> groupedSourcesById
                = sourceRepo.getSourcesByUserId(userId).stream()
                .collect(Collectors.groupingBy(Source::getType));
        log.info("get sources {}", groupedSourcesById.size());
        vkStrategy.update(userId, groupedSourcesById.get(SourceEnum.VK)
                .stream()
                .map(e -> (VkSource) e)
                .collect(Collectors.toList()));


    }
}
//
//import com.vk.api.sdk.client.VkApiClient;
//import com.vk.api.sdk.client.actors.UserActor;
//import com.vk.api.sdk.exceptions.ApiException;
//import com.vk.api.sdk.exceptions.ClientException;
//import com.vk.api.sdk.objects.wall.WallpostFull;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.GroupsCreatorData;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.HistoryCreatorData;
////import ru.road_to_manga.DB.models.i_will_delete_one_day.MainDB;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.TitleData;
//
//import java.util.AbstractMap;
//import java.util.ArrayList;
//import java.util.HashSet;
//
////class TitleInfo {
////    String name;
////    String hashtag;
////    String group;
////    int lastChapter;
////    String lastChapterLink;
////
////    int lastChapterID;
////    int lastChapterDate;
////    public TitleInfo(String name, String hashtag, String group, int lastChapter, String lastChapterLink, int lastChapterID, int lastChapterDate) {
////        this.name = name;
////        this.hashtag = hashtag;
////        this.group = group;
////        this.lastChapter = lastChapter;
////        this.lastChapterLink = lastChapterLink;
////        this.lastChapterID = lastChapterID;
////        this.lastChapterDate = lastChapterDate;
////    }
////}
////class TitleInfo {
////    String name = "";
////    String hashtag = "#ядумалаэтобудетобычныйисекай@comicstoon1";
////    String group = "comicstoon1";
////    int lastChapter = 12;
////    String lastChapterLink = "https://vk.com/wall-217422006?q=%23ядумалаэтобудетобычныйисекай&w=wall-217422006_6945";
////}
//public class Updater {
//    MainDB db;
//
//    VkApiClient vk;
//    UserActor actor;
//    Updater(VkApiClient vk, UserActor actor, MainDB db) {
//        this.vk = vk;
//        this.actor = actor;
//        this.db = db;
//    }
////    ArrayList<TitleInfo> array;
//    int lastChapter;
//
////    void getInfo(String title) {
////        this.lastChapter = 12;
////        // TODO: бд
////        this.array =  new ArrayList<>();
////        array.add(
////                new TitleInfo(
////                        "Я думала это будет обычный исекай",
////                        "#Я_думала_это_общее_достояние",
////                        "babytrouble",
////                        12,
////                        "https://vk.com/wall=-201997090_10011",
////                        10011,
////                        8229
////                )
////        );
////        array.add(
////                new TitleInfo(
////                        "Я думала это будет обычный исекай",
////                        "#ядумалаэтобудетобычныйисекай@comicstoon1",
////                        "comicstoon1",
////                        12,
////                        "https://vk.com/wall-217422006?offset=20&owners_only=1&q=%23ядумалаэтобудетобычныйисекай&w=wall-217422006_6945",
////                        6945,
////                        -1
////                )
////        );
////
////    }
//    void update(String titleName) throws ClientException, ApiException {
//        Searcher searcher = new Searcher(db, vk, actor);
//        TitleData info = db.getTitleData(titleName);
//        HashSet<Integer> chaptersToSearch = info.getMissedChapter();
//        Integer lastChapter = info.getAllMissedSince();
//        ArrayList<GroupsCreatorData> groups = db.getGroupsData(titleName);
//        ArrayList<HistoryCreatorData> newPosts = new ArrayList<>();
//        for (var group : groups) {
//            ArrayList<WallpostFull> buffer = searcher.getWallTillID(
//                    group.getOwnerID(), group.getSearchQuery(),
//                    group.getPostID(), group.getDate());
//            AbstractMap.SimpleImmutableEntry<ArrayList<HistoryCreatorData>,
//                    Integer> answer = searcher.ForwardSearchInBuffer(
//                            buffer, chaptersToSearch, lastChapter,
//                    group.getLastChapter(), 1);
//            newPosts.addAll(answer.getKey());
//            db.updateLastChapterInGroup(group.getOwnerID(), answer.getValue());
//            lastChapter = Math.max(lastChapter, answer.getValue());
//        }
//        info.setMissedChapter(chaptersToSearch);
//        info.setAllMissedSince(lastChapter);
//        db.updateTitleInfo(info);
//        Viewer.post(newPosts);
//
////        getInfo(titleName);
////        for (TitleInfo titleInfo : array) {
////            ArrayList<WallpostFull> posts = new ArrayList<>();
////            int offset = 0;
////            SearchingPosts: while (true) {
////                SearchResponse answer = vk.wall().search(actor)
////                        .domain(titleInfo.group)
////                        .query(titleInfo.hashtag)
////                        .count(10)
////                        .offset(offset)
////                        .execute();
////                offset += 10;
////                List<WallpostFull> lst = answer.getItems();
////                if (lst.isEmpty()) {
////                    break ;
////                }
////                for (WallpostFull element : lst) {
////                    if (element.getId() == titleInfo.lastChapterID || element.getDate() < titleInfo.lastChapterDate) {
////                        break SearchingPosts;
////                    }
////                    posts.add(element);
////                }
////            }
////            // if lastChapter exists;
////            int firstChapter = titleInfo.lastChapter + 1;
////            if (firstChapter == 0) {
////                // TODO
////            }
////            for (int i = posts.size() - (this.lastChapter - firstChapter + 2); i>=0; --i) {
////                // TODO: check chapter number;
////                post(posts.get(i));
////            }
////            this.lastChapter += posts.size() - (this.lastChapter - firstChapter) - 1;
////            System.out.println(this.lastChapter);
////        }
//
//    }
//
//}
