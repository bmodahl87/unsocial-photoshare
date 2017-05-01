package controllers;

import models.Following;
import models.Notification;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.App;

import javax.inject.Inject;

/**
 * Created by bmodahl on 4/27/17.
 */
public class UserController extends Controller {

    private FormFactory formFactory;

    @Inject
    public UserController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    @Security.Authenticated(Secured.class)
    public Result follow(String username) {

        Following follow = new Following(
                username,
                currentUser().username,
                User.authFind.ref(username));

        follow.save();

        Notification notification = new Notification();

        notification.setMessage(currentUser().username);
        notification.user = User.authFind.ref(username);

        notification.save();

        flash("following", "Now Following: " + username);

        return ok("Now following: " + username);
    }

    @Security.Authenticated(Secured.class)
    public Result unfollow(String username) {

        Following.authFind.where()
                            .eq("following_username", User.authFind.ref(request().username()).getUsername())
                            .and()
                            .eq("username", username)
                            .delete();

        return ok("Stopped Following: " + username);
    }


    public Result addUser() {

        DynamicForm requestData = formFactory.form().bindFromRequest();

        if (User.authFind.byId(requestData.get("username")) != null) {

            flash("userNotAdded", "Username unavailable!");

            return redirect(routes.Application.index());

        } else {

            User newUser = new User(
                    requestData.get("username"),
                    requestData.get("name"),
                    requestData.get("password"),
                    requestData.get("email"));

            newUser.save();

            flash("userAdded", "Successfully created account!");

            return redirect(routes.Login.login());
        }
    }

    private User currentUser() {
        return User.authFind.ref(request().username());
    }

}
