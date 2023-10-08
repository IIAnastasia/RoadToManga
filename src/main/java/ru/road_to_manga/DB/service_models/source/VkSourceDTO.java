package ru.road_to_manga.DB.service_models.source;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.road_to_manga.enums.SourceEnum;

@EqualsAndHashCode(callSuper = true)
@Data
public class VkSourceDTO extends SourceDTO {
    public int ownerId;
    public long lastPostDate;

    public VkSourceDTO() {
        this.setType(SourceEnum.VK);
    }
}
