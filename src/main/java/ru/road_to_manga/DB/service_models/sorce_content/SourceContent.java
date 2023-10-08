package ru.road_to_manga.DB.service_models.sorce_content;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.road_to_manga.DB.service_models.source.Source;
import ru.road_to_manga.enums.SourceEnum;

@Entity
@Table(name = "source_content")
@Inheritance
@Data
@RequiredArgsConstructor
public class SourceContent {
    @ManyToOne
    private final Source source;
    @Column(name = "type")
    private final SourceEnum type;
    @Id
    @GeneratedValue
    private long id;

    public SourceContent() {
        source = null;
        type = null;
    }

}
