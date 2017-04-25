package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmodahl on 4/5/17.
 */
@Entity
public class Following extends Model{


    @Id
    public int following_id;
    public String username;
    public String following_username;

    @ManyToOne(cascade= CascadeType.ALL)
    public User user;

    @OneToMany(mappedBy = "following", cascade= CascadeType.ALL)
    public List<Image> images() {
        return Image.find.where().eq("username", username).findList();
    }

    public int followers() {

        return Following.find.where().eq("following_username", following_username).findList().size();
    }

    public User userFollower() {

        return User.authFind.where().eq("username", following_username).findUnique();
    }

    public static Find<Integer, Following> find = new Find<Integer, Following>(){};

    public static Find<String, Following> authFind = new Find<String, Following>(){};


}
