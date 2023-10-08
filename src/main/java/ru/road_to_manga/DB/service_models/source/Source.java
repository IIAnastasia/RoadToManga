package ru.road_to_manga.DB.service_models.source;

import jakarta.persistence.*;
import lombok.Data;
import ru.road_to_manga.enums.SourceEnum;

@Entity
@Data
@Inheritance
@Table(name = "source")
public class Source {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "type")
    private SourceEnum type;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "name")
    private String name;


//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public SourceEnum getType() {
//        return type;
//    }
//
//    public void setType(SourceEnum type) {
//        this.type = type;
//    }
//
//    public long getSourceIDNumb() {
//        return sourceIDNumb;
//    }
//
//    public void setSourceIDNumb(long sourceIDNumb) {
//        this.sourceIDNumb = sourceIDNumb;
//    }
}
