package process.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import process.domain.Message;


/**
 * This is a Publisher class which will publish given payload to the topic.
 */
@Service
public class MessagePublisherImpl implements MessagePublisher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public MessagePublisherImpl() {
    }

    public MessagePublisherImpl(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    /**
     * Publish received message to the topic.
     * @param message
     */
    @Override
    public void publish(final Message message) {
        logger.info("Published: {}", message);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
