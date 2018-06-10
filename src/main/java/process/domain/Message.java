package process.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Message")
public class Message implements Serializable {

    @Id
    private Long messageId;
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message(Long messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
}
