package ru.road_to_manga.DB.service_models.indicator_model;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.road_to_manga.DB.vk_models.vk_indicator.VkIndicator;

@Component
@Slf4j
public class IndicatorMapper {
    final ModelMapper mapper;

    public IndicatorMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Indicator map(IndicatorDTO dto) {
        Indicator indicator = mapper.map(dto, switch (dto.getType()) {
            case VK -> VkIndicator.class;
            default -> Indicator.class;
        });
        return mapper.map(dto,
                switch (dto.getType()) {
                    case VK -> VkIndicator.class;
                    default -> Indicator.class;
                });
    }
}