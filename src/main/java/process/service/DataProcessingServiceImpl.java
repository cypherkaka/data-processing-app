package process.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import process.domain.Message;
import process.exception.InvalidRequestException;
import process.queue.MessagePublisher;
import process.repository.H2Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DataProcessingServiceImpl implements DataProcessingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Qualifier("redisPublisher")
    @Autowired
    private MessagePublisher messagePublisher;

    @Autowired
    H2Repository h2Repository;

    @Override
    public void process(final Message message) {
        validateMessage(message);
        messagePublisher.publish(message);
    }

    @Override
    public void process(final List<Message> messageList) {
        messageList.parallelStream().forEach(message -> {
            validateMessage(message);
            messagePublisher.publish(message);
        });
    }

    @Override
    public List<Message> getAllMessages(final int limit) {

        validateLimitValue(limit);
        logger.info("getAllMessages with limit {}", limit);

        List<Message> messages = StreamSupport.stream(h2Repository.findAll().spliterator(), false)
                .limit(limit)
                .collect(Collectors.toList());

        return messages;
    }


    private void validateLimitValue(int limit) {
        if (limit <= 0)
            throw new InvalidRequestException("Invalid limit value: " + limit + ", Should be limit > 0");
    }

    private void validateMessage(Message message) {
        if (message.getId() <= 0) {
            throw new InvalidRequestException("Invalid message id: " + message.getId() + ", Should be id > 0");
        }

        if (StringUtils.isBlank(message.getMessage())) {
            throw new InvalidRequestException("Invalid message: " + message.getMessage());
        }
    }
}
