package ru.road_to_manga.DB.service_models.title;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "title")
@Data
public class Title {
//    public void setMissedChapters(HashSet<Integer> missedChapters) {
//        this.missedChapters = missedChapters;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id")
    private long userID;

    @Column(name = "title_name")
    private String titleName;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> missedChapters = new HashSet<>();

    @Column(name = "all_missed_since")
    private int allMissedSince = 0;

//    public void setUserID(long userID) {
//        this.userID = userID;
//    }
//
//    public void setTitleName(String titleName) {
//        this.titleName = titleName;
//    }
//
//    public void setMissedChapters(Set<Integer> missedChapters) {
//        this.missedChapters = missedChapters;
//    }
//
//    public void setAllMissedSince(Integer allMissedSince) {
//        this.allMissedSince = allMissedSince;
//    }
}
