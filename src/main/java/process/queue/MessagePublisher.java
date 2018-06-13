package process.queue;

import process.domain.Message;

public interface MessagePublisher {

    void publish(final Message message);
}
