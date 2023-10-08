package ru.road_to_manga.DB.vk_models.vk_content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VkContentRepo extends JpaRepository<VkContent, Long> {
}
