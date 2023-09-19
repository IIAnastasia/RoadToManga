package ru.road_to_manga.DB;

import java.util.ArrayList;
import java.util.List;

public class MainDB implements IMainDB{
    @Override
    public HistoryCreatorData getHistoryData(Integer historyID) {
        return null;
    }

    @Override
    public ArrayList<GroupsCreatorData> getGroupsData(String title) {
        return null;
    }

    @Override
    public void updateHistory(HistoryData historyData) {

    }

    @Override
    public Integer addChapter(HistoryCreatorData historyData) {
        return null;
    }

    @Override
    public Integer addGroup(GroupsCreatorData groupData) {
        return null;
    }

    @Override
    public List<Integer> decreaseChaptersSince(HistoryData historyData) {
        return null;
    }

    @Override
    public void updateChapter(Integer historyID, HistoryCreatorData historyData) {

    }

    @Override
    public TitleData getTitleData(String titleName) {
        return null;
    }

    @Override
    public void updateLastChapterInGroup(Integer groupID, Integer lastChapter) {

    }
}
