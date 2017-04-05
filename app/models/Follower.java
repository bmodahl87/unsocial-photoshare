package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmodahl on 4/5/17.
 */
@Entity
public class Follower extends Model {

    @Id
    public int id;
    public String username;
    public String follower_username;

    @ManyToOne(optional=false)
    public User user;

    @OneToMany(mappedBy = "follower", cascade= CascadeType.ALL)
    public List<Image> images = new ArrayList<>();
}
