package controllers;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.Transaction;
import models.Image;
import models.User;
import org.apache.commons.io.FileUtils;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;


import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static play.libs.Json.toJson;


/**
 * Created by bmodahl on 2/4/17.
 */
public class Application extends Controller {


    private FormFactory formFactory;

    @Inject
    public Application(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result index() {
        return ok(index.render("Application index"));
    }

    @Security.Authenticated(Secured.class)
    public Result home() {
        List<Image> imageList =
                Ebean.find(Image.class)
                        .where().eq("username", request().username())
                        .findList();
        return ok(home.render(User.authFind.ref(request().username()), imageList));
    }

    @Security.Authenticated(Secured.class)
    public Result profileHome() {
        List<Image> imageList =
                Ebean.find(Image.class)
                        .where().eq("username", request().username())
                        .findList();
        return ok(profileHome.render(User.authFind.ref(request().username()), imageList));
    }

    public Result getProfile(String username) {
        List<Image> imageList =
                Ebean.find(Image.class)
                        .where().eq("username", username)
                        .findList();
        return ok(profileHome.render(User.authFind.ref(username), imageList));
    }

    @Security.Authenticated(Secured.class)
    public Result search() {
        DynamicForm requestData = formFactory.form().bindFromRequest();

        List<User> userList = Ebean.find(User.class)
                .where().contains("username", requestData.get("search")).findList();
        return ok(search.render(User.authFind.ref(request().username()), userList));
    }

    @Security.Authenticated(Secured.class)
    public Result followers() {
        return ok(followers.render(User.authFind.ref(request().username())));
    }

    @Security.Authenticated(Secured.class)
    public Result following() {
        return ok(following.render(User.authFind.ref(request().username())));
    }

    @Security.Authenticated(Secured.class)
    public Result upload() { return ok(upload.render(User.authFind.ref(request().username()))); }

    @Security.Authenticated(Secured.class)
    public Result imageUpload() throws MalformedURLException, IOException {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");

        if (picture != null) {
            String fileName = Integer.toString(picture.getFilename().hashCode()) + ".jpg";
            File file = picture.getFile();

            FileUtils.moveFile(file, new File("/Users/bmodahl/Desktop/fullImages/", fileName));

            try (InputStream in = new URL("http://localhost:8080/resizeImage/resizeImageJpeg?url=http://localhost:9000/fullImage?image=" + fileName + "&width=200&height=200").openStream()){

                Files.copy(in, Paths.get("/Users/bmodahl/Desktop/thumbImages/" + fileName));

                //FileUtils.moveFile(file, new File("/home/ubuntu/fullImages/", fileName));
            } catch (IOException ioe) {
                System.out.println("Problem operating on filesystem");
            }

            DynamicForm requestData = formFactory.form().bindFromRequest();

            Image image = new Image();



            image.full_image = fileName;
            image.thumb_image = fileName;
            image.username = User.authFind.ref(request().username()).getUsername();
            image.comments = requestData.get("comment");

            image.save();

            return ok(upload.render(User.authFind.ref(request().username())));

        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result notifications() {
        return ok(notifications.render(User.authFind.ref(request().username())));
    }

    public Result getThumbImage(String image) {
        return ok(new java.io.File("/Users/bmodahl/Desktop/thumbImages/" + image));
        //return ok(new java.io.File("/home/ubuntu/thumbImages/" + image));
    }

    public Result getFullImage(String image) {
        return ok(new java.io.File("/Users/bmodahl/Desktop/fullImages/" + image));
        //return ok(new java.io.File("/home/ubuntu/fullImages/" + image));
    }

    public Result addUser() {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if (User.authFind.byId(userForm.get().getUsername()) != null) {
            flash("userNotAdded", "Username unavailable!");
            return redirect(routes.Application.index());
        } else {
            userForm.get().save();
            flash("userAdded", "Successfully created account!");
            return redirect(routes.Application.login());
        }
    }



    public Result getUsers() {

        return ok(toJson(User.find.all()));
    }

    public Result deleteUser(Integer id) {
        User.find.ref(id).delete();
        flash("Success, user deleted");
        return redirect(routes.Application.index());
    }

    /**
     * Display the 'edit form' of a existing User.
     *
     * @param id Id of the user to edit
     */
    public Result editUser(Integer id) {
        Form<User> userForm = formFactory.form(User.class).fill(User.find.byId(id));
        return ok(views.html.userEdit.render(id, userForm));
    }

    public Result updateUser(Integer id) throws PersistenceException {

        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if(userForm.hasErrors()) {
            return badRequest("Form Has Errors!");
        }

        Transaction txn = Ebean.beginTransaction();
        try {
            User savedUser = User.find.byId(id);
            if (savedUser != null) {
                User newUserData = userForm.get();
                savedUser.setName(newUserData.name);
                savedUser.setEmail(newUserData.email);
                savedUser.setUsername(newUserData.username);
                savedUser.setPassword(newUserData.password);

                savedUser.update();

                txn.commit();
            }
        } finally {
            txn.end();
        }
        return ok("User Updated.");
    }

    public static class Login {

        public String username;
        public String password;

        public String validate() {
            if (User.authenticate(username, password) == null) {
                return "Invalid username or password";
            }
            return null;
        }

    }

    public Result login() {

        Form<Login> userForm = formFactory.form(Login.class);

        return ok(login.render(userForm));
    }


    public Result authenticate() {
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("username", loginForm.get().username);
            return redirect(
                    routes.Application.home()
            );
        }
    }

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.index()
        );
    }



}
