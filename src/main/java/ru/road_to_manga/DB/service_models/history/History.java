package ru.road_to_manga.DB.service_models.history;

import jakarta.persistence.*;
import lombok.Data;
import ru.road_to_manga.DB.service_models.sorce_content.SourceContent;
import ru.road_to_manga.DB.service_models.source_for_title.SourceForTitle;

import java.util.List;

@Entity
@Table(name = "history")
@Data
public class History {
    @ElementCollection
    List<Integer> chapters;
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private SourceForTitle sourceForTitle;
    @Column(name = "max_chapter")
    private int maxChapter;

    @OneToOne
    private SourceContent content;
//    @Column(name="user_id")
//    private long userID;
//    @Column(name="source_content_id")
//    private long contentID;
//    @Column(name="title")
//    private String title;
//    @ElementCollection
//    ArrayList<Integer> chapters;

}
