package ru.road_to_manga.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.road_to_manga.DB.service_models.indicator_model.IndicatorMapper;
import ru.road_to_manga.DB.service_models.indicator_model.IndicatorRepo;
import ru.road_to_manga.DB.service_models.sorce_content.SourceContentRepo;
import ru.road_to_manga.DB.service_models.source.Source;
import ru.road_to_manga.DB.service_models.source.SourceDTO;
import ru.road_to_manga.DB.service_models.source.SourceMapper;
import ru.road_to_manga.DB.service_models.source.SourceRepo;
import ru.road_to_manga.DB.service_models.source_for_title.SourceForTitle;
import ru.road_to_manga.DB.service_models.source_for_title.SourceForTitleRepo;
import ru.road_to_manga.DB.service_models.title.AddTitleDTO;
import ru.road_to_manga.DB.service_models.title.Title;
import ru.road_to_manga.DB.service_models.title.TitleRepo;
import ru.road_to_manga.DB.service_models.title_in_source.TitleInSource;
import ru.road_to_manga.DB.service_models.title_in_source.TitleInSourceRepo;
import ru.road_to_manga.dto.AddSourceForTitleDTO;

@Service
@Slf4j
public class Manager {
    final TitleRepo titleRepo;
    final SourceRepo sourceRepo;
    final SourceForTitleRepo sourceForTitleRepo;
    final SourceContentRepo sourceContentRepo;
    final TitleInSourceRepo titleInSourceRepo;
    final IndicatorRepo indicatorRepo;
    final ModelMapper mapper;
    final SourceMapper sourceMapper;
    final IndicatorMapper indicatorMapper;


    public Manager(TitleRepo titleRepo,
                   SourceRepo sourceRepo,
                   SourceForTitleRepo sourceForTitleRepo, SourceContentRepo sourceContentRepo,
                   TitleInSourceRepo titleInSourceRepo,
                   IndicatorRepo indicatorRepo, ModelMapper mapper,
                   SourceMapper sourceMapper, IndicatorMapper indicatorMapper) {
        this.titleRepo = titleRepo;
        this.sourceRepo = sourceRepo;
        this.sourceForTitleRepo = sourceForTitleRepo;
        this.sourceContentRepo = sourceContentRepo;
        this.titleInSourceRepo = titleInSourceRepo;
        this.indicatorRepo = indicatorRepo;
        this.mapper = mapper;
        this.sourceMapper = sourceMapper;
        this.indicatorMapper = indicatorMapper;
    }
    //
//    @Autowired
//    Manager(TitleRepo titleRepository,
//            SourceRepo sourceRepository,
//            SourceContentRepo sourceContentRepository,
//            TitleInSourceRepo titleInSourceRepository,
//            IndicatorRepo indicatorRepository) {
//        this.titleRepository = titleRepository;
//        this.sourceRepository = sourceRepository;
//
//    }

    public Title addTitleToMonitoring(AddTitleDTO titleDTO) {
//        return titleRepository.save(AddTitleMapper.mapper.dtoToModel(titleDTO));
        return titleRepo.save(mapper.map(titleDTO, Title.class));
    }


    public Source addSourceToMonitoring(SourceDTO sourceDTO) {
        return sourceRepo.save(sourceMapper.map(sourceDTO));
//        return sourceRepository.save(mapper.map(sourceDTO,
//                        switch (sourceDTO.getType()) {
//                            case VK -> VkSource.class;
//                            default -> Source.class;
//                        }
//                )
//        );
//        return sourceRepository.save(mapper.map(
//                        switch (sourceDTO.getType()) {
//                            case VK -> (VkSourceDTO) sourceDTO;
//                            default -> sourceDTO;
//                        }
//                )
//        );
    }
//    public void addTitleInSource(AddTitleInSourceDTO addTitleInSourceDTO) {
//        Title title;
//        if (addTitleInSourceDTO.isTitleNew()) {
//            title = addTitleToMonitoring(new AddTitleDTO(
//                    addTitleInSourceDTO.getUserID(),
//                    addTitleInSourceDTO.getTitleName()));
//        } else {
//            Optional<Title> optional = titleRepository
//                    .findTitleModelByUserIDAndTitleName(
//                    addTitleInSourceDTO.getUserID(),
//                    addTitleInSourceDTO.getTitleName());
//            if (optional.isPresent()) {
//                title = optional.get();
//            }
//            // TODO: handle not present;
//        }
//        Source model;
//        if (addTitleInSourceDTO.getSourceDTO() != null) {
//
//        }
//        SourceForTitle sourceForTitleModel = new SourceForTitle();
//        sourceForTitleModel.setTitle(tit);
//    }

    public SourceForTitle addSourceForTitle(AddSourceForTitleDTO addSourceForTitleDTO) {
        SourceForTitle sourceForTitle = new SourceForTitle();
        sourceForTitle.setTitle(
                titleRepo.saveOrFindByUserIDAndTitleName(
                        addSourceForTitleDTO.getTitleDTO()
                )
        );
        TitleInSource titleInSource = new TitleInSource();
        titleInSource.setUserID(addSourceForTitleDTO.getUserId());
        titleInSource.setSource(
                (addSourceForTitleDTO.getSourceDTO().getType() == null ?
                        sourceRepo.findByName(addSourceForTitleDTO
                                .getSourceDTO().getName()) :
                        addSourceToMonitoring(addSourceForTitleDTO.getSourceDTO())
                )
        );
        titleInSource.setIndicator(
                indicatorRepo.save(indicatorMapper.map(
                        addSourceForTitleDTO.getIndicatorDTO()))
        );
        titleInSource.setBlocked(false);
        sourceForTitle.setTitleInSource(
                titleInSourceRepo.save(titleInSource)
        );
        return sourceForTitleRepo.save(sourceForTitle);
    }



/*
CREATE FUNCTION process_insert()
RETURNS TRIGGER AS $$
DECLARE
    special_id INTEGER;
BEGIN
    CASE NEW.type
        WHEN 1 THEN
            -- выполнять действия для специального типа 1
            -- например, вставка данных в таблицу special_table1
			INSERT INTO child2 (name) VALUES (NEW.name) RETURNING id INTO special_id;
        ELSE
            -- обработка типов, которые не совпадают с условиями выше
            RAISE EXCEPTION 'Unknown type: %', NEW.type;
    END CASE;
	INSERT INTO parent2 (type, child_id)
    VALUES (NEW.type, special_id);

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_source_trigger
BEFORE INSERT ON parent2
FOR EACH ROW
EXECUTE FUNCTION process_insert();
 */


//    public void addTitleInGroupToMonitoring(TitleInSourceDTO titleInSourceDTO) {
//        long sourceID = sourceRepository.saveOrGetID(
//                titleInSourceDTO.getSourceContentDTO().getSourceDTO());
//        long sourceContentID = sourceContentRepository.saveOrGet
//
//
//    }


}

