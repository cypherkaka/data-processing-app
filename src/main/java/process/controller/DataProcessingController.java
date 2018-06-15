package process.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import process.domain.Message;
import process.service.DataProcessingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/data-processing-app")
public class DataProcessingController {

    @Autowired
    DataProcessingService dataProcessingService;

    @RequestMapping(path = "/testme", method = RequestMethod.GET)
    String testMe() {
        return "Hi from Container! CurrentTime: " + LocalDateTime.now();
    }

    @RequestMapping(path = "/process", method = RequestMethod.POST)
    ResponseEntity<String> processMessage(@RequestBody Message message) throws JsonProcessingException {

        dataProcessingService.process(message);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/process-all", method = RequestMethod.POST)
    ResponseEntity<String> processAllMessage(@RequestBody List<Message> messageList) throws JsonProcessingException {

        dataProcessingService.process(messageList);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/message/get-all")
    List<Message> getAllMessages(@RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {

        return dataProcessingService.getAllMessages(limit);
    }
}
