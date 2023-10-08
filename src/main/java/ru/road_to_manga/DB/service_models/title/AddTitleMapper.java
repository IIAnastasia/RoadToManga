package ru.road_to_manga.DB.service_models.title;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface AddTitleMapper {
    AddTitleMapper mapper = Mappers.getMapper(AddTitleMapper.class);

    Title dtoToModel(AddTitleDTO dto);

    @AfterMapping
    default void setMissedChapters(AddTitleDTO source,
                                   @MappingTarget Title target) {
        System.out.println("abracadabra");
        target.setMissedChapters(new HashSet<Integer>());
    }

    AddTitleDTO modelToDto(Title model);
}
