package process.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import process.domain.Message;

/**
 * Main service layer for data processing pipeline app.
 */
public interface DataProcessingService {

    void process(Message message) throws JsonProcessingException;
}
