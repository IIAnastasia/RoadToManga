package ru.road_to_manga.DB.vk_models.vk_indicator;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.road_to_manga.DB.service_models.indicator_model.Indicator;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class VkIndicator extends Indicator {
    String query;
}
