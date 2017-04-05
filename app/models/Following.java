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
    public int id;
    public String username;
    public String following_username;

    @ManyToOne(optional=false)
    public User user;

    @OneToMany(mappedBy = "following", cascade= CascadeType.ALL)
    public List<Image> images() {
        return Image.find.where().eq("username", following_username).findList();
    }

}
