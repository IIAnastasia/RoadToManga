package ru.road_to_manga.DB.service_models.indicator_model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicatorRepo extends JpaRepository<Indicator, Long> {
}
