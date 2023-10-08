package ru.road_to_manga.DB.service_models.indicator_model;


import jakarta.persistence.*;
import lombok.Data;
import ru.road_to_manga.enums.SourceEnum;

@Entity
@Inheritance
@Data
public class Indicator {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "type")
    private SourceEnum type;
}
