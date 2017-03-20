package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

import static play.mvc.Controller.flash;

/**
 * Created by bmodahl on 2/8/17.
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        flash("unauth", "You are not logged in!");
        return redirect(routes.Application.index());
    }


}
