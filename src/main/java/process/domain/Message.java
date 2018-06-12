package process.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("Message")
public class Message implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
