package models;

import com.google.inject.Inject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bmodahl on 5/4/17.
 */
public class NotificationTest {

    @Inject
    Notification test = new Notification();

    @Test
    public void setMessage() throws Exception {

        test.setMessage("testing");
        assertTrue("Message set correctly", test.message == "testing is now following you.");

    }

}