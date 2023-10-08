package ru.road_to_manga.DB.service_models.sorce_content;

import org.modelmapper.ModelMapper;
import ru.road_to_manga.DB.vk_models.vk_content.VkContent;

public class SourceContentMapper {
    final ModelMapper mapper;

    public SourceContentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public SourceContent map(SourceContentDTO dto) {
        return mapper.map(dto,
                switch (dto.getType()) {
                    case VK -> VkContent.class;
                    default -> SourceContent.class;
                });
    }
}
