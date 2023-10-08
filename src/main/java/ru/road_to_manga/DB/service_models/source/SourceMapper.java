package ru.road_to_manga.DB.service_models.source;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.road_to_manga.DB.vk_models.vk_source.VkSource;

@Component
public class SourceMapper {
    final ModelMapper mapper;

    public SourceMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Source map(SourceDTO dto) {
        return mapper.map(dto,
                switch (dto.getType()) {
                    case VK -> VkSource.class;
                    default -> Source.class;
                });

    }
}

//@Mapper(componentModel = "spring")
//public interface SourceMapper {
//    SourceMapper mapper = Mappers.getMapper(SourceMapper.class);
//
////    @Mapping(target = "type", expression = "java(ru.road_to_manga.enums.SourceEnum.VK)")
////    SourceDTO vkToSourceDTO(VkSourceDTO vkDTO);
//
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "type", expression = "java(ru.road_to_manga.enums.SourceEnum.VK)")
//    VkSource dtoToModel(VkSourceDTO sourceDTO);
//    @Mapping(target = "id", ignore = true)
//    Source dtoToModel(SourceDTO sourceDTO);
//
//
//
//
////    @Mapping(target = "type", ignore = true)
////    @Mapping(source = "sourceIDNumb", target = "sourceIDNumb")
////    SourceDTO modelToDto(Source sourceModel);
//}
