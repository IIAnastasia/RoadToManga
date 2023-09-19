package ru.road_to_manga.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TitleData {
    String title;
    HashSet<Integer> missedChapter;
    Integer allMissedSince;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashSet<Integer> getMissedChapter() {
        return missedChapter;
    }

    public void setMissedChapter(HashSet<Integer> missedChapter) {
        this.missedChapter = missedChapter;
    }

    public Integer getAllMissedSince() {
        return allMissedSince;
    }

    public void setAllMissedSince(Integer allMissedSince) {
        this.allMissedSince = allMissedSince;
    }
}
