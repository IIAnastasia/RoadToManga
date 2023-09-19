package ru.road_to_manga;

import com.vk.api.sdk.objects.wall.WallpostFull;

import java.util.ArrayList;

public class ChapterAnalyser {
    public static ArrayList<Integer> parseChapterNumbers(WallpostFull post,
                                                  int predictedNumber) {
        // TODO: analyseText
        ArrayList<Integer> answer = new ArrayList<>(1);
        answer.add(predictedNumber);
        return answer;
    }
}
