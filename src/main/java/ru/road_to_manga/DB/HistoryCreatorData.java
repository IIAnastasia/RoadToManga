package ru.road_to_manga.DB;

import java.util.ArrayList;

public class HistoryCreatorData {
    Integer userID;
    String title;
    ArrayList<Integer> chapters;
    Integer ownerID;
    Integer postID;
    Integer date;
    public HistoryCreatorData(){}

    public HistoryCreatorData(Integer userID, String title,
                              ArrayList<Integer> chapters,
                              Integer ownerID, Integer postID, Integer date) {
        this.userID = userID;
        this.title = title;
        this.chapters = chapters;
        this.ownerID = ownerID;
        this.postID = postID;
        this.date = date;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public ArrayList<Integer> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Integer> chapters) {
        this.chapters = chapters;
    }
}