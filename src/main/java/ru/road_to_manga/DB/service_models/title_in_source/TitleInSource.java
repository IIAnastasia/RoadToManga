package ru.road_to_manga.DB.service_models.title_in_source;

import jakarta.persistence.*;
import lombok.Data;
import ru.road_to_manga.DB.service_models.indicator_model.Indicator;
import ru.road_to_manga.DB.service_models.source.Source;

@Entity
@Table(name = "title_in_source")
@Data
public class TitleInSource {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id")
    private long userID;

    @ManyToOne
    private Source source;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @OneToOne
    private Indicator indicator;
}
