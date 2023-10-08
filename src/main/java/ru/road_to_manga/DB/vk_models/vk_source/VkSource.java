package ru.road_to_manga.DB.vk_models.vk_source;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import ru.road_to_manga.DB.service_models.source.Source;

@Entity
@Data
public class VkSource extends Source {
    @Column(name = "owner_id")
    private int ownerId;

    @Column(name = "last_post_date")
    private long lastPostDate;
}
