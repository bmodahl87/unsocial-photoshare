package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmodahl on 4/30/17.
 */
@Entity
public class Album extends Model {

    @Id
    public int id;
    public String name;


    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(nullable=true)
    public User user;

    @OneToMany(mappedBy = "album", cascade= CascadeType.ALL)
    public List<Image> images = new ArrayList<>();


    public static Find<Integer, Album> find = new Find<Integer, Album>(){};

    public static Find<String, Album> findByString = new Find<String, Album>(){};


}
