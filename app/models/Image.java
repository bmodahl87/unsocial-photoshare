package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;


/**
 * Created by bmodahl on 2/7/17.
 */
@Entity
public class Image extends Model {

    @Id
    public int id;
    public String username;
    public String full_image;
    public String thumb_image;
    public String comments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable=true)
    public User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable=true)
    public Album album;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable=true)
    public Following following;

    public static Find<String, Image> find = new Find<String, Image>(){};

    public static Find<Integer, Image> findID = new Find<Integer, Image>(){};



//    /**
//     * Gets userid.
//     *
//     * @return the id
//     */
//    public int getId() {
//        return id;
//    }
//
//    /**
//     * Sets userid.
//     *
//     * @param id the userid
//     */
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    /**
//     * Gets userid.
//     *
//     * @return the id
//     */
//    public int getUser_id() {
//        return user_id;
//    }
//
//    /**
//     * Sets userid.
//     *
//     * @param user_id the userid
//     */
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
//
//    /**
//     * Gets full image.
//     *
//     * @return the full image
//     */
//    public String getFull_image() {
//        return full_image;
//    }
//
//    /**
//     * Sets full image.
//     *
//     * @param full_image the full image
//     */
//    public void setFull_image(String full_image) {
//        this.full_image = full_image;
//    }
//
//    /**
//     * Gets thumb image.
//     *
//     * @return the thumb image
//     */
//    public String getThumb_image() {
//        return thumb_image;
//    }
//
//    /**
//     * Sets thumb image.
//     *
//     * @param thumb_image the thumb image
//     */
//    public void setThumb_image(String thumb_image) {
//        this.thumb_image = thumb_image;
//    }
//
//    /**
//     * Gets comments.
//     *
//     * @return the comments
//     */
//    public String getComments() {
//        return comments;
//    }
//
//    /**
//     * Sets comments.
//     *
//     * @param comments the comments
//     */
//    public void setComments(String comments) {
//        this.comments = comments;
//    }





}
