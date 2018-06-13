package process.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import process.domain.Message;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "Message";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void add(final Message message) {
        hashOperations.put(KEY, message.getId(), message.getMessage());
    }

    public void delete(final String id) {
        hashOperations.delete(KEY, id);
    }

    public Message findMessage(final String id) {
        return (Message) hashOperations.get(KEY, id);
    }

    public Map<Object, Object> findAllMessages() {
        return hashOperations.entries(KEY);
    }


}
