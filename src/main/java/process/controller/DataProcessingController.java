package process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import process.domain.Message;
import process.service.DataProcessingService;

import java.util.List;

/**
 * Entry point for Data processing pipeline app.  There are two main API provided by this app. <br/>
 * 1. Process message <br/>
 * 2. Get all message <br/>
 * <p>
 * Each of above API are interacting with in-memory database H2 (@see <a href="http://h2database.com/html/main.html">http://h2database.com/html/main.html</a>) <br/>
 * and Redis (@see <a href="https://redis.io">https://redis.io</a>)
 */
@RestController
@RequestMapping("/data-processing-app")
public class DataProcessingController {

    @Autowired
    DataProcessingService dataProcessingService;

    /**
     * This API will process given JSon and perform following operations.  <br/>
     * 1. Process received message and send it to the queue for respective subscriber to process <br/>
     * 2. Redis subscriber(@see process.queue.RedisSubscriber#onMessage()) will save it to Redis  <br/>
     * 3. H2 subscriber(@see process.queue.H2Subscriber#onMessage()) will save it to in-memory database <br/>
     * 4. Web subscriber(@see process.queue.WebSubscriber#onMessage()) will push received message to listening browser clients through Websocket <br/>
     * <p>
     * <p>
     * After performing all above operation this API will return HTTP response back to caller(i.e. RESTClient/Curl/3rd Party services)
     * <p>
     * <br/>
     * Supported HTTP Method: <code>POST</code>
     * <p>
     * <br/>
     * Sample URL: <code>http://localhost:8080/data-processing-app/process</code>
     *
     * @param message a Message payload to process
     * @return HTTP response code
     *
     * <code> {"id":1,"message":"MESSAGE 1"} </code>
     * <p>
     * <br/><br/>
     * - HTTP Status 200: Success
     */
    @RequestMapping(path = "/process", method = RequestMethod.POST)
    ResponseEntity<String> processMessage(@RequestBody Message message) {

        dataProcessingService.process(message);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * This API will process given list of JSon and perform following operations.  <br/>
     * 1. Process received message and send it to the queue for respective subscriber to process <br/>
     * 2. Redis subscriber(@see process.queue.RedisSubscriber#onMessage()) will save it to Redis  <br/>
     * 3. H2 subscriber(@see process.queue.H2Subscriber#onMessage()) will save it to in-memory database <br/>
     * 4. Web subscriber(@see process.queue.WebSubscriber#onMessage()) will push received message to listening browser clients through Websocket <br/>
     * <p>
     * <p>
     * After performing all above operation this API will return HTTP response back to caller(i.e. RESTClient/Curl/3rd Party services)
     * <p>
     * <br/>
     * Supported HTTP Method: <code>POST</code>
     * <p>
     * <br/>
     * Sample URL: <code>http://localhost:8080/data-processing-app/process-all</code>
     *
     * @param messageList a List of message payload to process
     * @return HTTP response code
     *
     * <code>[ {"id":1,"message":"MESSAGE 1"} , {"id":2,"message":"MESSAGE 2"} ] </code>
     * <p>
     * <br/><br/>
     * - HTTP Status 200: Success
     */
    @RequestMapping(path = "/process-all", method = RequestMethod.POST)
    ResponseEntity<String> processAllMessage(@RequestBody List<Message> messageList) {

        dataProcessingService.process(messageList);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * This API retrieve all processed messages from in-memory database H2 (@see <a href="http://h2database.com/html/main.html">http://h2database.com/html/main.html</a>) <br/>
     * and return all messages as Array of JSon <br/>
     * <p>
     * To limit number of messages you can also pass <code>limit<code/> as query parameter.  Default value of <code>limit</code> is 20.
     * <p>
     * <p>
     * <br/>
     * Supported HTTP Method: <code>GET</code>
     * <p>
     * <br/>
     * Sample URL: <code>http://localhost:8080/data-processing-app/message/get-all</code>
     * *
     *
     * @param limit Optional query parameter.  Default value is 20.
     * @return Array of JSon objects
     */
    @RequestMapping(path = "/message/get-all")
    List<Message> getAllMessages(@RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {

        return dataProcessingService.getAllMessages(limit);
    }
}
