package process.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import process.queue.*;
import process.repository.H2Repository;
import process.repository.RedisRepository;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.pub-sub-queue}")
    private String redisPubSubQueue;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private H2Repository h2Repository;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    MessageListenerAdapter redisMessageListener() {
        return new MessageListenerAdapter(new RedisSubscriber(redisRepository));
    }

    @Bean
    MessageListenerAdapter webMessageListener() {
        return new MessageListenerAdapter(new WebSubscriber(simpMessagingTemplate));
    }

    @Bean
    MessageListenerAdapter h2MessageListener() {
        return new MessageListenerAdapter(new H2Subscriber(h2Repository));
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new MessagePublisherImpl(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(redisPubSubQueue);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(redisMessageListener(), topic());
        container.addMessageListener(webMessageListener(), topic());
        container.addMessageListener(h2MessageListener(), topic());
        return container;
    }
}