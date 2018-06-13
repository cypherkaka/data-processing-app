package process.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import process.domain.Message;
import process.queue.MessagePublisher;
import process.repository.RedisRepository;

@Service
public class DataProcessingServiceImpl implements DataProcessingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Qualifier("redisPublisher")
    @Autowired
    private MessagePublisher messagePublisher;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void process(Message message) throws JsonProcessingException {

        logger.info("From Process: redisRepository:  {}",redisRepository);

        messagePublisher.publish(message);
    }
}
