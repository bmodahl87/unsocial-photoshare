package controllers;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import javax.inject.Inject;

/**
 * Created by bmodahl on 4/27/17.
 */
public class Login extends Controller {

    public String username;
    public String password;

    private FormFactory formFactory;

    public Login(){}

    @Inject
    public Login(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String validate() {
        if (User.authenticate(username, password) == null) {
            return "Invalid username or password";
        } else {
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
