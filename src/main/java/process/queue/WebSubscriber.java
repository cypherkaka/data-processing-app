package process.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

@Service
public class WebSubscriber implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        process.domain.Message messagePayload = (process.domain.Message) SerializationUtils.deserialize(message.getBody());
        logger.info("Received for processing: {}", messagePayload);

        try {
            simpMessagingTemplate.convertAndSend("/topic/payload-messages", new ObjectMapper().writeValueAsString(messagePayload));
        } catch (JsonProcessingException e) {
            logger.error("Error processing message to web", e);
        }
    }
}
