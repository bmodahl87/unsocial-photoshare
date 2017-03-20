package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Created by bmodahl on 2/4/17.
 */

@Entity
public class User extends Model {

    @Id
    public String username;
    public String name;
    public String password;
    public String email;


    /**
     * Generic query helper for entity Computer with id Long
     */
    public static Find<Integer, User> find = new Find<Integer, User>(){};

    public static Find<String, User> authFind = new Find<String, User>(){};


    public static User authenticate(String username, String password) {
        return authFind.where().eq("username", username)
                .eq("password", password).findUnique();
    }


    /**
     * Gets first name.
     *
     * @return the full name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets first name.
     *
     * @param name the first name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets user name.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the first name
     */
    public void setPassword(String password) {
        this.password = password;
    }





}
