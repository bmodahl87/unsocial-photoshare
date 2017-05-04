package models;

import com.google.inject.Inject;
import org.junit.Test;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * Created by bmodahl on 5/4/17.
 */
public class UserTest {

    @Inject
    User user;


    @Test
    public void following() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

    @Test
    public void followers() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

    @Test
    public void isFollowing() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

}