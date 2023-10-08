package ru.road_to_manga.DB.service_models.title;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepo extends JpaRepository<Title, Long> {
    Optional<Title> findTitleModelByUserIDAndTitleName(long userId, String name);

    //    Optional<Title> find
    default Title saveOrFindByUserIDAndTitleName(AddTitleDTO addTitleDTO) {
        Optional<Title> modelOpt = findTitleModelByUserIDAndTitleName(
                addTitleDTO.getUserID(), addTitleDTO.getTitleName());
        return modelOpt.orElseGet(() ->
                save(AddTitleMapper.mapper.dtoToModel(addTitleDTO)));
    }
}
