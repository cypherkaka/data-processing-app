package process.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import process.domain.Message;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Service
public class DataProcessingServiceImpl implements DataProcessingService {

    private static final String KEY = "messages";

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Long, Message> hashOperations;

    @Autowired
    private SimpMessagingTemplate template;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void process(Message message) throws JsonProcessingException {
        hashOperations.put(KEY, message.getId(), message);

        template.convertAndSend("/topic/payload-messages", new ObjectMapper().writeValueAsString(message));
    }
}
