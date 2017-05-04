package controllers;


import com.avaje.ebean.Ebean;
import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;
import javax.inject.Inject;
import java.util.List;



/**
 * Created by bmodahl on 2/4/17.
 */

public class Application extends Controller {

    //ToDo: LOGGING.

    private FormFactory formFactory;

    @Inject
    public Application(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    // Methods to render views:

    public Result index() {
        return ok(index.render("Application index"));
    }

    @Security.Authenticated(Secured.class)
    public Result home() {

        return ok(home.render(currentUser(), imageList()));
    }

    @Security.Authenticated(Secured.class)
    public Result profileHome() {

        return ok(profileHome.render(currentUser(), imageList()));
    }

    @Security.Authenticated(Secured.class)
    public Result getProfile(String username) {

        return ok(getProfile.render(User.authFind.ref(username), imageList()));
    }

    @Security.Authenticated(Secured.class)
    public Result album(Integer id) {
        Album album = Album.find.where().eq("id", id).findUnique();

        return ok(views.html.album.render(currentUser(), album));
    }

    @Security.Authenticated(Secured.class)
    public Result search() {

        return ok(search.render(currentUser(), userList()));
    }

    @Security.Authenticated(Secured.class)
    public Result followers() {

        return ok(followers.render(currentUser(), followerList()));
    }

    @Security.Authenticated(Secured.class)
    public Result following() {

        return ok(following.render(currentUser(), followingList()));
    }


    @Security.Authenticated(Secured.class)
    public Result upload() { return ok(upload.render(currentUser())); }


    @Security.Authenticated(Secured.class)
    public Result notifications() {
        // Mark notifications as viewed
        List<Notification> newNotifications = Notification.find.where().eq("has_been_viewed", false).and().eq("user_username", currentUser().getUsername()).findList();

        for(Notification notification : newNotifications) {
            notification.hasBeenViewed = true;
            notification.save();
        }

        return ok(notifications.render(currentUser()));
    }

    // Lists used for views:

    protected List<Image> imageList() {
        return Image.find.where().eq("username", request().username()).findList();
    }

    protected List<User> userList() {

        DynamicForm requestData = formFactory.form().bindFromRequest();

        return User.authFind.where().contains("username", requestData.get("search")).findList();
    }

    protected List<Following> followerList() {
        return Ebean.find(Following.class).where().eq("username", request().username()).findList();
    }

    protected List<Following> followingList() {
        return Ebean.find(Following.class).where().eq("following_username", request().username()).findList();
    }

    protected User currentUser() {
        return User.authFind.ref(request().username());
    }

}
