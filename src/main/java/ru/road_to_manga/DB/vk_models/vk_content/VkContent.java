package ru.road_to_manga.DB.vk_models.vk_content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.road_to_manga.DB.service_models.sorce_content.SourceContent;
import ru.road_to_manga.DB.service_models.source.Source;
import ru.road_to_manga.enums.SourceEnum;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class VkContent extends SourceContent {
    @Column(name = "owner_id")
    private final long ownerId;
    @Column(name = "post_id")
    private final long postId;
    @Column(name = "post_date")
    private long postDate;
    public VkContent(Source source, long ownerId, long postId, long postDate) {
        super(source, SourceEnum.VK);
        this.ownerId = ownerId;
        this.postId = postId;
        this.postDate = postDate;
    }

    public VkContent() {
        ownerId = 0;
        postDate = 0;
        postId = 0;
    }

}
