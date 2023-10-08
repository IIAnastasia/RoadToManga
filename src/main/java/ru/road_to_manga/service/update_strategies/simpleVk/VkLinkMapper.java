package ru.road_to_manga.service.update_strategies.simpleVk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.utils.responses.ResolveScreenNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class VkLinkMapper {

    private final UserActor godActor;
    private final VkApiClient vk;

    Pair<Integer, Integer> parseLink(String link) throws ClientException, ApiException {
        Matcher mather = Pattern.compile("wall(\\d+)_(\\d+)").matcher(link);
        if (mather.find()) {
            int ownerId = Integer.parseInt(mather.group(0));
            int postId = Integer.parseInt(mather.group(1));
            return Pair.of(ownerId, postId);
        }
        mather = Pattern.compile("wall(\\d+)").matcher(link);
        if (mather.find()) {
            int ownerId = Integer.parseInt(mather.group(0));
            return Pair.of(ownerId, -1);
        }
        mather = Pattern.compile("vk\\.com/(\\w*)").matcher(link);
        if (mather.find()) {
            String ownerDomain = mather.group(0);
            ResolveScreenNameResponse info = vk.utils()
                    .resolveScreenName(godActor, ownerDomain).execute();
            int ownerId;
            if (info.getType().getValue().equals("group")) {
                ownerId = info.getGroupId();
            } else {
                ownerId = info.getObjectId();
            }
            return Pair.of(ownerId, -1);
        }
        return Pair.of(-1, -1);
    }

    String toLink(int ownerId, int postId) {
        return "https://vk.com/wall" + ownerId + "_" + postId;
    }

}
