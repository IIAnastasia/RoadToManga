package ru.road_to_manga;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
@ConfigurationProperties(prefix = "conf")
@Slf4j
public class Config {
    @Value("${conf.godVkId}")
    private int godVkId;
    @Value("${conf.godVkAccess}")
    private String godVkAccess;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(STRICT);
        return mapper;
    }


    @Bean
    public VkApiClient getTransportClient() {
        return new VkApiClient(new HttpTransportClient());
    }

    @Bean
    public UserActor getGodActor() {
        return new UserActor(godVkId, godVkAccess);
    }

}
