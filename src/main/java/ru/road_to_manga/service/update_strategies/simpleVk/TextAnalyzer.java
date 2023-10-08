package ru.road_to_manga.service.update_strategies.simpleVk;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

// TODO: бор
// Пока просто проверка
@Slf4j
public class TextAnalyzer {
    private final List<String> indicators;

    public TextAnalyzer(List<String> indicators) {
        this.indicators = indicators;
    }

    public List<Integer> getIndicatorIndexes(String text) {
        List<Integer> answer = new ArrayList<>();
        for (int i = 0; i < indicators.size(); ++i) {
            if (text.contains(indicators.get(i))) {
                answer.add(i);
            }
        }
        return answer;
    }
}
