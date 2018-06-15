package process.controller;

import com.google.gson.Gson;
import junitparams.JUnitParamsRunner;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import process.DataProcessingApp;
import process.domain.Message;
import process.service.DataProcessingService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnitParamsRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DataProcessingApp.class)
@AutoConfigureMockMvc
public class DataProcessingControllerTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private MockMvc mvc;

    @Autowired
    DataProcessingService dataProcessingService;

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Test
    public void processMessageTest() throws Exception {

        Message message = new Message();
        message.setId(1L);
        message.setMessage("MESSAGE 1");

        String messageAsJson = new Gson().toJson(message);

        mvc.perform(post("/data-processing-app/process")
                .contentType(MediaType.APPLICATION_JSON).content(messageAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void processAllMessageTest() throws Exception {

        Message message1 = new Message();
        message1.setId(1L);
        message1.setMessage("MESSAGE 1");

        Message message2 = new Message();
        message2.setId(2L);
        message2.setMessage("MESSAGE 2");

        List<Message> messageList = Arrays.asList(message1, message2);

        String messageListAsJson = new Gson().toJson(messageList);

        mvc.perform(post("/data-processing-app/process-all")
                .contentType(MediaType.APPLICATION_JSON).content(messageListAsJson))
                .andExpect(status().isOk());
    }


    @Test
    public void getAllMessages() throws Exception {
        mvc.perform(get("/data-processing-app/message/get-all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMessagesWithLimit() throws Exception {
        mvc.perform(get("/data-processing-app/message/get-all?limit=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
