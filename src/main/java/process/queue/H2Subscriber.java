package process.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import process.repository.H2Repository;

/**
 * This subscriber will process received payload and store it into in-memory database H2 (@see <a href="http://h2database.com/html/main.html">http://h2database.com/html/main.html</a>) <br/>
 */
@Service
public class H2Subscriber implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final H2Repository h2Repository;

    public H2Subscriber(H2Repository h2Repository) {
        this.h2Repository = h2Repository;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        process.domain.Message messagePayload = (process.domain.Message) SerializationUtils.deserialize(message.getBody());
        logger.info("Received for processing: {}", messagePayload);

        h2Repository.save(messagePayload);
    }
}
