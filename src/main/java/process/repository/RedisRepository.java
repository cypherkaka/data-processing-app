package process.repository;

import process.domain.Message;

import java.util.Map;

public interface RedisRepository {

    /**
     * Return all messages
     */
    Map<Object, Object> findAllMessages();

    /**
     * Add key-value pair to Redis.
     */
    void add(Message message);

    /**
     * Delete a key-value pair in Redis.
     */
    void delete(String id);

    /**
     * find a message
     */
    Message findMessage(String id);

}
