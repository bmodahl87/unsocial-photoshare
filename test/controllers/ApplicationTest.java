package controllers;

import com.google.inject.Inject;
import models.User;
import org.junit.Test;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * Created by bmodahl on 5/4/17.
 */
public class ApplicationTest {

    @Inject
    User user;

    @Test
    public void imageList() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

    // This method uses form data from the search field; unsure how to handle that.
    @Test
    public void userList() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

    @Test
    public void followerList() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

    @Test
    public void followingList() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

    @Test
    public void currentUser() throws Exception {

        running(fakeApplication(inMemoryDatabase("test")), () -> {

            user = User.authFind.ref(fakeRequest().username());



        });

    }

}