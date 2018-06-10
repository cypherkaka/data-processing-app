package process.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import process.domain.Message;
import process.service.DataProcessingService;
import process.service.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/data-processing-app")
public class DataProcessingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataProcessingService dataProcessingService;

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(path = "/testme", method = RequestMethod.GET)
    String testMe() {
        return "Hi from Container! CurrentTime: " + LocalDateTime.now();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(path = "/process", method = RequestMethod.POST)
    String processMessage(@RequestBody Message message) {

        //TODO: to be implemented

        throw new UnsupportedOperationException();
    }

    @RequestMapping(path = "/message/get-all")
    List<Message> getAllMessages() {

        //TODO: to be implemented

        throw new UnsupportedOperationException();
    }
}
