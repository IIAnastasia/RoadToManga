package ru.road_to_manga.DB.vk_models.vk_source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VkSourceRepo extends JpaRepository<VkSource, Long> {
    List<VkSource> findByUserId(Long userId);
}
