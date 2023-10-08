package ru.road_to_manga.DB.service_models.source_for_title;

import jakarta.persistence.*;
import lombok.Data;
import ru.road_to_manga.DB.service_models.title.Title;
import ru.road_to_manga.DB.service_models.title_in_source.TitleInSource;

@Entity
@Table(name = "source_for_title")
@Data
public class SourceForTitle {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Title title;

    @OneToOne
    private TitleInSource titleInSource;

}
