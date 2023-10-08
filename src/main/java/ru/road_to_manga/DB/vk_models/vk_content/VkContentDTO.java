package ru.road_to_manga.DB.vk_models.vk_content;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.road_to_manga.DB.service_models.sorce_content.SourceContentDTO;

@EqualsAndHashCode(callSuper = true)
@Data
public class VkContentDTO extends SourceContentDTO {
    private long postId;
    private long postDate;
    private long ownerId;
}
