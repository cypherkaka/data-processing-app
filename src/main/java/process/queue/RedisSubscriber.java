package process.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import process.repository.RedisRepository;

@Service
public class RedisSubscriber implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisRepository redisRepository;

    public RedisSubscriber(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        process.domain.Message messagePayload = (process.domain.Message) SerializationUtils.deserialize(message.getBody());
        logger.info("RedisSubscriber Received: {}", messagePayload);
        redisRepository.add(messagePayload);
    }

}
