package ru.road_to_manga.DB.vk_models.vk_indicator;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.road_to_manga.DB.service_models.indicator_model.IndicatorDTO;

@EqualsAndHashCode(callSuper = true)
@Data
public class VkIndicatorDTO extends IndicatorDTO {
    String query;
}
