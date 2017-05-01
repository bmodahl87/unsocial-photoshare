package models;

import com.avaje.ebean.Model;
import net.sf.ehcache.search.expression.Not;

import javax.persistence.*;
import java.util.List;

/**
 * Created by bmodahl on 4/25/17.
 */
@Entity
public class Notification extends Model {


    @Id
    public int id;
    public boolean hasBeenViewed = false;
    public String message;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(nullable=true)
    public User user;

    public void setMessage(String username) {

        this.message = username + " is now following you.";
    }

    public static Find<Integer, Notification> find = new Find<Integer, Notification>(){};

    public static Find<String, Notification> authFind = new Find<String, Notification>(){};


}
