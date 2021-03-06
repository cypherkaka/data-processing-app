package process.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import process.repository.RedisRepository;


/**
 * This subscriber will process received payload and store it into Redis server (@see <a href="https://redis.io/">https://redis.io</a>) <br/>
 */
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
        logger.info("Received for processing: {}", messagePayload);

        redisRepository.add(messagePayload);
    }
}
