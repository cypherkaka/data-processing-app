package process.service;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.web.WebAppConfiguration;
import process.domain.Message;
import process.exception.InvalidRequestException;
import process.queue.MessagePublisher;
import process.repository.H2Repository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(JUnitParamsRunner.class)
@WebAppConfiguration
public class DataProcessingServiceImplTest {

    @InjectMocks
    private DataProcessingServiceImpl dataProcessingServiceImpl;

    @Mock
    private MessagePublisher messagePublisher;

    @Mock
    H2Repository h2Repository;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    Message message;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void processMessageTest() {

        Message message = new Message();
        message.setId(1);
        message.setMessage("MESSAGE 1");

        dataProcessingServiceImpl.process(message);

        verify(messagePublisher).publish(any(Message.class));
    }

    @Test
    @Parameters({
            "0", "-9", "-111"
    })
    public void processMessageWithInvalidMessageIdTest(int messageId) {

        expectedException.expect(InvalidRequestException.class);

        Message message = new Message();
        message.setId(messageId);
        message.setMessage("MESSAGE 1");

        dataProcessingServiceImpl.process(message);
    }

    @Test
    @Parameters({
            "", "   "
    })
    public void processMessageWithInvalidMessageTextTest(String messageText) {

        expectedException.expect(InvalidRequestException.class);

        Message message = new Message();
        message.setId(1);
        message.setMessage(messageText);

        dataProcessingServiceImpl.process(message);
    }

    @Test
    public void getAllMessagesTest() {
        dataProcessingServiceImpl.getAllMessages(20);

        verify(h2Repository).findAll();
    }

    @Test
    @Parameters({
            "0", "-9", "-111"
    })
    public void getAllMessagesWithInvalidLineNumberTest(int line) {
        expectedException.expect(InvalidRequestException.class);

        dataProcessingServiceImpl.getAllMessages(line);
    }
}
