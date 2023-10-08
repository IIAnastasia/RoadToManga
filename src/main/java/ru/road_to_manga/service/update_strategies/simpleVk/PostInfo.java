package ru.road_to_manga.service.update_strategies.simpleVk;

import com.vk.api.sdk.objects.wall.WallpostFull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j

public class PostInfo {
    private final static Pattern mainPattern;
    private final static Pattern numberPattern;
    // Алгоритм: ищем максимально близкие к слову глава.
    // С главой ассоциирована numericString - номера записанные через запятую и тире.
    // Писать главы 1⭐2⭐3 вряд ли будут.
    // Писать главы (1, 3) - (6, 8) вряд ли будут
    // Писать 1-4-5 вряд ли будут
    // Формат в который верим: через запятую записаны числа или диапазоны
    // Разделитель - пробел
    // Однако между словом "глава" и numericString могут быть
    // какие-то эмодзи или двоеточие (separatorString).
    // Проверяем что это не слова, числа, точки или запятые
    private final static Pattern numericPattern;

    // в каждом паттерне выделяем в группы rangeString
    static {
        // паттерн числа
        String numberString = "\\d+";
        // паттерн "число - число" или просто "число"
        String rangeDashString = numberString +
                "(:?\\s*-\\s*" + numberString + ")?";
        // паттерн "с число по число"
        String rangeWordString = "(?:с|from)\\s*" + numberString +
                "\\s*(?:по|to)\\s*" + numberString;
        // паттерн 2 диапазонов. Из найденному по регулярному
        // выражению вхождению будем выбирать диапазоны через .group
        String rangeString = "(" + rangeDashString + "|" + rangeWordString + ")";
        // паттерн записанных через запятую диапазонов
        String numericString = numberString + "(:?\\s*,\\s*" + rangeString + ")?";
        String separator = "[^\\p{L}\\d.,]*";
        String word = "(?:глав[аыу]|chapters?)";
//        String range = "(?:с)|(?:from)\\s*(\\d+)\\s*(?:по)|(?:to)\\s*(\\d+)";

        numericPattern = Pattern.compile(numericString, Pattern.CASE_INSENSITIVE);
        mainPattern = Pattern.compile(word + separator + numericString
                + "|" + numericString + separator + word);
        numberPattern = Pattern.compile("(\\d+)");


//        List<String> words = List.of("глав[аыу]", "chapter[s]?");
//        List<String> ranges = List.of(
//                "с\\s*(\\d+)\\s*по\\s*(\\d+)",
//                "from\\s*(\\d+)\\s*to\\s*(\\d+)"
//        );
//        String separator = "[^\\p{L}\\d]*";
//        String numericString = "(\\d+)(\\s*-\\s*(\\d+))?";
//        numericPattern = Pattern.compile(
//                numericString, Pattern.CASE_INSENSITIVE);
//        List<String> groups1_2String = new ArrayList<>();
//        List<String> groups1_3String = new ArrayList<>();
//        for (int i = 0; i < words.size(); ++i) {
//            groups1_3String.add(words.get(i) + separator + numericString);
//            groups1_3String.add(numericString + separator + words.get(i));
//            groups1_2String.add(words.get(i) + separator + ranges.get(i));
//            groups1_2String.add(ranges.get(i) + separator + words.get(i));
//        }
//        Function<List<String>, List<Pattern>> toPattern = (strings) ->
//                (strings.stream().map((stringPattern) -> Pattern.compile(
//                        stringPattern, Pattern.CASE_INSENSITIVE)).toList());
//        groups1_2 = toPattern.apply(groups1_2String);
//        groups1_3 = toPattern.apply(groups1_3String);
    }

    private final String text;
    @Getter
    private final int ownerId;
    @Getter
    private final int postId;
    @Getter
    private final int postDate;
    @Getter
    private List<Integer> chapters = new ArrayList<>();


    PostInfo(WallpostFull wallpostFull) {
        this.text = wallpostFull.getText();
        this.ownerId = wallpostFull.getOwnerId();
        this.postId = wallpostFull.getId();
        this.postDate = wallpostFull.getDate();
    }

    public void analyseChapters(int predictedChapter) {
        log.info("predict chapters by {} mainPattern {} for text {}",
                predictedChapter, mainPattern, text);
        List<Integer> prediction = predictChapters(mainPattern, predictedChapter);
        if (prediction.isEmpty()) {
            log.info("predict chapters by {} numericPattern {} for text {}",
                    predictedChapter, numberPattern, text);
            prediction = predictChapters(numericPattern, predictedChapter);
        }
        if (prediction.isEmpty()) {
            if (predictedChapter != -1) {
                chapters.add(predictedChapter);
            }
        } else {
            chapters = prediction;
        }

    }

    private List<Integer> predictChapters(Pattern pattern, int predictedChapter) {

        List<Integer> prediction = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);
        boolean foundPredicted = false;
        while (!foundPredicted && matcher.find()) {
            List<Integer> newCandidate = new ArrayList<>();
            for (int rangeNumber = 0; rangeNumber < matcher.groupCount();
                 ++rangeNumber) {
                String range = matcher.group(rangeNumber);
                if (range == null) {
                    continue;
                }
                log.info("range {}", range);
                // TODO: что лучше: ручками if и split или еще один matcher?
                Matcher numbers = numberPattern.matcher(range);
                numbers.find();
                int startNumber = Integer.parseInt(numbers.group());
                log.info("start {}", startNumber);
                int endNumber = numbers.find() ?
                        Integer.parseInt(numbers.group()) : startNumber;
                log.info("end {}", endNumber);
                if (startNumber <= predictedChapter && endNumber >= predictedChapter) {
                    foundPredicted = true;
                }
                for (int number = startNumber; number <= endNumber; ++number) {
                    newCandidate.add(number);
                }
            }
            if (prediction.isEmpty() || foundPredicted) {
                prediction = newCandidate;
            }
        }
        return prediction;
    }

    public void analyseChapters() {
        analyseChapters(-1);
    }
}
