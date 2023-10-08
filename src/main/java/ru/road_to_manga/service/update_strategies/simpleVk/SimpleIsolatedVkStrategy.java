package ru.road_to_manga.service.update_strategies.simpleVk;


import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.queries.wall.WallGetQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.road_to_manga.DB.service_models.history.History;
import ru.road_to_manga.DB.service_models.history.HistoryRepo;
import ru.road_to_manga.DB.service_models.source_for_title.SourceForTitle;
import ru.road_to_manga.DB.service_models.source_for_title.SourceForTitleRepo;
import ru.road_to_manga.DB.service_models.title.Title;
import ru.road_to_manga.DB.service_models.title.TitleRepo;
import ru.road_to_manga.DB.service_models.title_in_source.TitleInSource;
import ru.road_to_manga.DB.service_models.title_in_source.TitleInSourceRepo;
import ru.road_to_manga.DB.vk_models.vk_content.VkContent;
import ru.road_to_manga.DB.vk_models.vk_content.VkContentRepo;
import ru.road_to_manga.DB.vk_models.vk_indicator.VkIndicator;
import ru.road_to_manga.DB.vk_models.vk_source.VkSource;
import ru.road_to_manga.DB.vk_models.vk_source.VkSourceRepo;
import ru.road_to_manga.dto.ViewInfo;
import ru.road_to_manga.service.Viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

// Просто по каждому source у пользователя идет чтение каждого поста
// На очередном посту проверяется вхождение строки запроса для тайтлов пользователя,
// Если нашли обрабатываем
// Если не нашли и source не заблокирован для мониторинга
// - для заблокированных у пользователя.
// Если не нашли и source не заблокирован для мониторинга - пост выводится
@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleIsolatedVkStrategy {
    public final Viewer viewer;
    public final VkLinkMapper vkLinkMapper;
    private final VkSourceRepo sourceRepository;
    private final TitleInSourceRepo titleInSourceRepo;
    private final HistoryRepo historyRepo;
    private final VkContentRepo vkContentRepo;
    private final SourceForTitleRepo sourceForTitleRepo;
    private final TitleRepo titleRepo;
    private final UserActor godActor;
    private final VkApiClient vk;

    //    @Value("${GOD_VK_ID}")
//    private long godId;
//    @Value("${GOD_VK_ACCESS}")
//    private String godAccessToken;
    public void update(long userId, List<VkSource> sources) {
        UserActor actor = accessVk(userId);
        sources.forEach((source) -> {
            try {
                updateSource(source, actor);
            } catch (ClientException | ApiException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Планируется, что пользователь должен будет авторизоваться для
    // доступа к Vk. Donut и закрытым сообществам
    // Если не хочет, то пользуется accessToken-ом сервера

    // Пока не реализуем авторизацию
    private UserActor accessVk(long userId) {
        // TODO: logic
        return godActor;
    }

    private void updateSource(VkSource source, UserActor actor) throws ClientException, ApiException, InterruptedException {
        log.info("update source {}", source.getName());
        List<TitleInSource> titleInSources = titleInSourceRepo
                .findBySource(source);
        log.info("get titleInSource {}", titleInSources.size());
        TextAnalyzer analyzer = new TextAnalyzer(
                titleInSources
                        .stream()
                        .map((titleInSourceModel ->
                                ((VkIndicator) titleInSourceModel.getIndicator()).getQuery()))
                        .toList());

        Map<Integer, List<PostInfo>> titleInfo = new HashMap<>();
        WallGetQuery query = vk.wall().get(actor)
                .ownerId(source.getOwnerId()).count(100);
        int offset = 0;
        List<WallpostFull> vkAnswer;
        boolean mayBePinned = true;
        SearchUntilDate:
        while (!(vkAnswer = query.offset(offset).execute().getItems()).isEmpty()) {
            log.info("read {} posts", offset);
            offset += vkAnswer.size();
            for (WallpostFull post : vkAnswer) {
                log.info("found post {} {} {}", post.getOwnerId(), post.getId(), post.getDate());
                if (post.getDate() < source.getLastPostDate() && !mayBePinned) {
                    break SearchUntilDate;
                }
                // В titleInSource лежат либо заблокированные по query либо
                // добавленные в какй-то тайтл
                boolean unmarked = true;
                for (int index : analyzer.getIndicatorIndexes(post.getText())) {
                    if (!titleInSources.get(index).isBlocked()) {
                        log.info(post.toPrettyString());
                        PostInfo postInfo = new PostInfo(post);
                        log.info("postInfo {}", postInfo);
                        List<PostInfo> posts = titleInfo.getOrDefault(index, new ArrayList<>());
                        posts.add(new PostInfo(post));
                        if (posts.size() == 1) {
                            titleInfo.put(index, posts);
                        }
                        log.info("after adding {}", titleInfo.get(index));
                    }
                    unmarked = false;
                }
                if (unmarked && !source.isBlocked()) {
                    PostInfo postInfo = new PostInfo(post);
                    postInfo.analyseChapters();
                    post(postInfo, source, null);
                }
            }
            mayBePinned = false;
            Thread.sleep(1000);
        }
        updateTitles(titleInSources, titleInfo);
//                        .getItems().isEmpty())
    }

    private void updateTitles(List<TitleInSource> titleInSource,
                              Map<Integer, List<PostInfo>> titleInfo) {
        log.info("update titles{}", titleInSource.toString());
        log.info("update titles{}", titleInfo.toString());

        for (Map.Entry<Integer, List<PostInfo>> entries : titleInfo.entrySet()) {
            log.info("update {}", entries);

            SourceForTitle sourceForTitle = sourceForTitleRepo
                    .getSourceForTitleByTitleInSource(titleInSource.get(
                            entries.getKey()));
            log.info("source for title {}", sourceForTitle);
            int predicted = historyRepo
                    .findHistoryBySourceForTitleOrderByMaxChapterDesc(
                            sourceForTitle)
                    .map(History::getMaxChapter)
                    .orElse(-1);
            log.info("predicted {}", predicted);
            for (int index = entries.getValue().size() - 1; index >= 0; --index) {
                PostInfo postInfo = entries.getValue().get(index);
                postInfo.analyseChapters(predicted);
                predicted = postInfo.getChapters().stream().max(Integer::compare).orElse(0);
                boolean shouldPost = false;
                Title title = sourceForTitle.getTitle();
                log.info("Title {}", title);
                // TODO: посмотреть что лучше перегнать в Set
                for (Integer chapter : postInfo.getChapters()) {
                    if (title.getMissedChapters().contains(chapter)) {
                        shouldPost = true;
                        title.getMissedChapters().remove(chapter);
                    } else if (chapter > title.getAllMissedSince()) {
                        shouldPost = true;
                        title.getMissedChapters().addAll(IntStream
                                .range(title.getAllMissedSince() + 1,
                                        chapter - 1).boxed().toList());
                        title.setAllMissedSince(chapter);
                    }
                }
                if (shouldPost) {
                    post(postInfo,
                            (VkSource) sourceForTitle.getTitleInSource().getSource(),
                            sourceForTitle);
                }
            }
            titleRepo.save(sourceForTitle.getTitle());
        }


    }

    private void post(PostInfo postInfo, VkSource vkSource,
                      SourceForTitle sourceForTitle) {
        log.info("post posts {}", sourceForTitle);
        History history = new History();
        VkContent vkContent = new VkContent(
                vkSource, postInfo.getOwnerId(), postInfo.getPostId(),
                postInfo.getPostDate());
        vkContent = vkContentRepo.save(vkContent);
        history.setChapters(postInfo.getChapters());
        history.setContent(vkContent);
        history.setSourceForTitle(sourceForTitle);
        history.setMaxChapter(postInfo.getChapters().stream()
                .max(Integer::compare).orElse(0));
        historyRepo.save(history);

        String titleName = null;
        if (sourceForTitle != null) {
            titleName = sourceForTitle.getTitle().getTitleName();
        }
        log.info("post posts {}", titleName);
        viewer.post(new ViewInfo(
                vkLinkMapper.toLink(postInfo.getOwnerId(), postInfo.getPostId()),
                titleName,
                postInfo.getChapters()
        ));

    }
}
