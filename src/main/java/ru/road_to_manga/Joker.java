package ru.road_to_manga;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.road_to_manga.DB.service_models.source.VkSourceDTO;
import ru.road_to_manga.DB.service_models.title.AddTitleDTO;
import ru.road_to_manga.DB.vk_models.vk_indicator.VkIndicatorDTO;
import ru.road_to_manga.dto.AddSourceForTitleDTO;
import ru.road_to_manga.enums.SourceEnum;
import ru.road_to_manga.service.Manager;
import ru.road_to_manga.service.Updater;

@Service
@RequiredArgsConstructor
@Slf4j
public class Joker {
    @Autowired
    Joker(Manager manager, Updater updater) {
        long userId = 160;
        AddTitleDTO addTitleDTO = new AddTitleDTO();
        addTitleDTO.setTitleName("Чистый злодей");
        addTitleDTO.setUserID(userId);
        VkSourceDTO sourceDTO = new VkSourceDTO();
        sourceDTO.setName("Уютная читальня");
        sourceDTO.setType(SourceEnum.VK);
        sourceDTO.setOwnerId(-213467722);
        sourceDTO.setLastPostDate(1696674000);
        sourceDTO.setBlocked(true);
        sourceDTO.setUserId(userId);

        VkIndicatorDTO vkIndicatorDTO = new VkIndicatorDTO();
        vkIndicatorDTO.setType(SourceEnum.VK);
        vkIndicatorDTO.setQuery("#чистыйзлодей@yutnaya_chitalnya");
        AddSourceForTitleDTO addSourceForTitleDTO = new AddSourceForTitleDTO();
        addSourceForTitleDTO.setTitleDTO(addTitleDTO);
        addSourceForTitleDTO.setSourceDTO(sourceDTO);
        addSourceForTitleDTO.setUserId(userId);
        addSourceForTitleDTO.setContentDTO(null);
        addSourceForTitleDTO.setIndicatorDTO(vkIndicatorDTO);

        manager.addSourceForTitle(addSourceForTitleDTO);
        updater.Update(userId);
    }
}
