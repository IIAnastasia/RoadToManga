//package ru.road_to_manga.service;
//
//import com.vk.api.sdk.client.VkApiClient;
//import com.vk.api.sdk.client.actors.UserActor;
//import com.vk.api.sdk.exceptions.ApiException;
//import com.vk.api.sdk.exceptions.ClientException;
//import com.vk.api.sdk.objects.wall.WallpostFull;
//import com.vk.api.sdk.objects.wall.responses.SearchResponse;
//import com.vk.api.sdk.queries.wall.WallSearchQuery;
//import ru.road_to_manga.enums.Constants;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.GroupsCreatorData;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.HistoryCreatorData;
//import ru.road_to_manga.DB.models.i_will_delete_one_day.MainDB;
//
//import java.util.*;
//
//public class Searcher {
//    MainDB db;
//    VkApiClient vk;
//    UserActor actor;
//    Searcher(MainDB db, VkApiClient vk, UserActor actor) {
//        this.vk = vk;
//        this.actor = actor;
//        this.db = db;
//    }
//
//
//    public ArrayList<WallpostFull> getWallTillID(
//            Integer ownerID, String searchPattern, Integer endID,
//            Integer endDate
//    ) throws ClientException, ApiException {
//        WallSearchQuery query = vk.wall().search(actor)
//                .ownerId(ownerID)
//                .query(searchPattern)
//                .count(10);
//
//        // Find post which chapter number we know.
//        // Remember all newer posts.
//        SearchResponse vkAnswer;
//        int previousChapterDate = 1 << 31;
//        ArrayList<WallpostFull> answer = new ArrayList<>();
//        SearchingTrustyPost: while (
//                !(vkAnswer = query.offset(answer.size()).execute())
//                        .getItems().isEmpty()
//        ) {
//            for (WallpostFull element : vkAnswer.getItems()) {
//                if (
//                        Objects.equals(element.getId(), endID) ||
//                                (element.getDate() < endDate &&
//                                        previousChapterDate > endDate)
//                ) {
//                    break SearchingTrustyPost;
//                }
//                answer.add(element);
//                previousChapterDate = element.getDate();
//            }
//        }
//        return answer;
//    }
//
//    public AbstractMap.SimpleImmutableEntry<ArrayList<HistoryCreatorData>,
//            Integer> ForwardSearchInBuffer(
//            ArrayList<WallpostFull> buffer, HashSet<Integer> chaptersToSearch,
//            Integer searchAllSince,
//            int predictedChapter, int predictDelta
//    ) {
//
//
//        ArrayList<HistoryCreatorData> chaptersToPost = new ArrayList<>();
//        for (WallpostFull element : buffer) {
//            predictedChapter += predictDelta;
//            ArrayList<Integer> chapters = ChapterAnalyser.parseChapterNumbers(
//                    element, predictedChapter);
//            boolean used = false;
//            for (Integer chapter : chapters) {
//                if (chaptersToSearch.contains(chapter)) {
//                    chaptersToSearch.remove(chapter);
//                    used = true;
//                } else if (chapter > searchAllSince) {
//                    for (int unhandledChapter = searchAllSince + 1;
//                         unhandledChapter < chapter; ++unhandledChapter) {
//                        chaptersToSearch.add(unhandledChapter);
//                    }
//                    used = true;
//                    searchAllSince = chapter;
//                }
//            }
//            if (used) {
//                // TODO: with userID
//                chaptersToPost.add(new HistoryCreatorData(-1, "",
//                        chapters, element.getOwnerId(), element.getPostId(),
//                        element.getDate()));
//            }
//        }
//        return new AbstractMap.SimpleImmutableEntry<>(chaptersToPost, predictedChapter);
//    }
//    public void Search(String title, HashSet<Integer> chaptersToSearch,
//                       HistoryCreatorData trustedChapter)
//            throws ClientException, ApiException {
//        ArrayList<GroupsCreatorData> groups = db.getGroupsData(title);
//        ArrayList<HistoryCreatorData> newToPost = new ArrayList<>();
//
//        // TODO: what if появился новый пост во время выполнения
//        for (GroupsCreatorData group : groups) {
//            if (chaptersToSearch.isEmpty()) {
//                break;
//            }
//
//            // Find post which chapter number we know.
//            // Remember all newer posts.
//            ArrayList<WallpostFull> earlyPosts = getWallTillID(
//                    group.getOwnerID(), group.getSearchQuery(),
//                    trustedChapter.getPostID(), trustedChapter.getDate()
//            );
//
//
//
//
//            // Analyse chapters in newer posts.
//            int predictedChapter = trustedChapter.getChapters().get(
//                    trustedChapter.getChapters().size() - 1
//            );
//            int predictDelta = 1;
//            var earlyChapters = ForwardSearchInBuffer(earlyPosts,
//                    chaptersToSearch, Constants.INF,
//                    predictedChapter, predictDelta);
//            newToPost.addAll(earlyChapters.getKey());
//
//
//            // Get elder posts and analyse its chapters
//            WallSearchQuery query = vk.wall().search(actor)
//                    .ownerId(group.getOwnerID())
//                    .query(group.getSearchQuery())
//                    .count(10);
//            int offset = earlyPosts.size();
//            predictedChapter = trustedChapter.getChapters().get(0);
//            predictDelta = -1;
//            SearchResponse vkAnswer;
//            while (!chaptersToSearch.isEmpty() &&
//                    !(vkAnswer = query.offset(offset).execute())
//                            .getItems().isEmpty()
//            ) {
//
//                ArrayList<WallpostFull> buffer =
//                        (ArrayList<WallpostFull>) vkAnswer.getItems();
//                offset += buffer.size();
//                Collections.reverse(buffer);
//                var rightChapters = ForwardSearchInBuffer(buffer,
//                        chaptersToSearch, Constants.INF,
//                        predictedChapter, predictDelta);
//                newToPost.addAll(rightChapters.getKey());
//                predictedChapter = rightChapters.getValue();
//            }
//        }
//        Viewer.post(newToPost);
//    }
//}
