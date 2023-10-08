package ru.road_to_manga.DB.service_models.source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepo extends JpaRepository<Source, Long> {
    Source findByName(String name);

    //    @Query("select s from Source s WHERE s.userId = :user_id GROUP BY s.type ")
    List<Source> getSourcesByUserId(long userId);
//    Optional<Source> findByTypeAndSourceIDNumb(SourceEnum type,
//                                          long sourceIDNumb);
//    default long saveOrFindByNameID(SourceDTO sourceDTO) {
//        Optional<Source> modelOpt = findByTypeAndSourceIDNumb(
//                sourceDTO.getType(), sourceDTO.getSourceIDNumb());
//        if (modelOpt.isEmpty()) {
//            Source model = save(SourceMapper.mapper.dtoToModel(sourceDTO));
//            return model.getId();
//        }
//        return modelOpt.get().getId();
//    }
}

