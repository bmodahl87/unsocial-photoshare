package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
    public String profile_image;
    public String wall_image;


    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    public List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    public List<Notification> notifications = new ArrayList<>();

    public List<Notification> newNotifications() {
        return Notification.find.where().eq("has_been_viewed", false).and().eq("user_username", username).findList();
    }

    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    public List<Album> albums = new ArrayList<>();

    public List<Image> imageSlide() {
        return Image.find.setMaxRows(4).where().eq("username", username).findList();
    }

    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    public List<Following> following() {
        return Following.find.where().eq("following_username", username).findList();
    }

    public List<Following> followers() {
        return Following.find.where().eq("username", username).findList();
    }

    public boolean isFollowing(User thisUsername) {
        Following following = Following.authFind.where()
                .eq("user", thisUsername)
                .and()
                .eq("following_username", username)
                .findUnique();

        if (following != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generic query helper for entity Computer with id Long
     */
    public static Find<Integer, User> find = new Find<Integer, User>(){};

    public static Find<String, User> authFind = new Find<String, User>(){};


    public static User authenticate(String username, String password) {
        return authFind.where().eq("username", username)
                .eq("password", password).findUnique();
    }

    public User() {}


    public User(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.profile_image = "placeholder-user.png";
        this.wall_image = "placeholder.gif";
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
    public void setUsername(String username) { this.username = username; }

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

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getProfile_image() {
        return profile_image;
    }

    /**
     * Sets password.
     *
     * @param profile_image the first name
     */
    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    /**
     * Gets password.
     *
     * @return the wall_image
     */
    public String getWall_image() {
        return wall_image;
    }

    /**
     * Sets password.
     *
     * @param wall_image the first name
     */
    public void setWall_image(String wall_image) {
        this.wall_image = wall_image;
    }





}
