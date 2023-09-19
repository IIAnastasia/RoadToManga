package ru.road_to_manga.DB;


import java.util.ArrayList;
import java.util.List;

public interface IMainDB {
    public HistoryCreatorData getHistoryData(Integer historyID);

    public ArrayList<GroupsCreatorData> getGroupsData(String title);
    public void updateHistory(HistoryData historyData);
    public Integer addChapter(HistoryCreatorData historyData);
    public Integer addGroup(GroupsCreatorData groupData);
    public List<Integer> decreaseChaptersSince(HistoryData historyData);
    public void updateChapter(Integer historyID, HistoryCreatorData historyData);

    public TitleData getTitleData(String titleName);

    public void updateLastChapterInGroup(Integer groupID, Integer lastChapter);
    public void updateTitleInfo(TitleData titleData);
    
}
