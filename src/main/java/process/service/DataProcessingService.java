package process.service;

import process.domain.Message;

import java.util.List;

/**
 * Main service layer for data processing pipeline app.
 */
public interface DataProcessingService {

    void process(Message message);

    void process(List<Message> messageList);

    List<Message> getAllMessages(int limit);
}
