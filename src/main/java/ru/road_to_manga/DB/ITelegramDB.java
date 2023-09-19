package ru.road_to_manga.DB;

public interface ITelegramDB {
    public void addID(Integer messageID, Integer historyID);
    public Integer getID(Integer messageID);
}
