package models;

import com.google.inject.Inject;
import org.junit.Test;
import play.test.*;

import java.util.List;

import static play.test.Helpers.*;




import static org.junit.Assert.*;

/**
 * Created by bmodahl on 5/4/17.
 */
public class FollowingTest extends WithApplication {


    @Inject
    User user;

    @Test
    public void images() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());

            List<Image> test = user.images;

            assertTrue("Contains images for that user.", test != null);



        });

    }

    @Test
    public void followers() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());

            user.followers();

        });
    }

    @Test
    public void userFollower() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });
    }

}