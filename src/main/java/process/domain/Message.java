package process.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * A message to process through different channels.
 */
@Entity
public class Message implements Serializable {

    @Id
    private long id;

    private String message;

    private String generatedDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGeneratedDateTime() {
        return generatedDateTime;
    }

    public void setGeneratedDateTime(String generatedDateTime) {
        this.generatedDateTime = generatedDateTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", generatedDateTime=" + generatedDateTime +
                '}';
    }
}
