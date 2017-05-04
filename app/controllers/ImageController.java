package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import models.Album;
import models.Image;
import models.User;
import org.apache.commons.io.FileUtils;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Created by bmodahl on 4/27/17.
 */
public class ImageController extends Controller {

    private FormFactory formFactory;
    private Logger logger;

    @Inject
    public ImageController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    @Security.Authenticated(Secured.class)
    public Result imageUpload() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> pictures = body.getFiles();

        if (pictures != null) {

            DynamicForm requestData = formFactory.form().bindFromRequest();
            Album album = new Album();

            if (!requestData.get("album").isEmpty()) {

                album.name = requestData.get("album");
                album.user = User.authFind.ref(request().username());

                if (!requestData.get("description").isEmpty()) {
                    album.description = requestData.get("description");
                    album.save();
                } else {
                    album.save();
                }

            }

            for (Http.MultipartFormData.FilePart picture : pictures) {

                String fileExt = picture.getContentType().substring(6);
                String fileName = UUID.randomUUID().toString() + "." + fileExt;

                    saveUploadedImage(picture, fileName);

                    // Thumbnails need a height > 186px
                    saveThumbnail(fileName, 200);

                    Image image = new Image();

                    image.full_image = fileName;
                    image.thumb_image = fileName;
                    image.username = User.authFind.ref(request().username()).getUsername();


                    if (!requestData.get("album").isEmpty()) {

                        image.album = album;

                    } else if (requestData.get("albums") == "00100110101") {
                        image.album = null;
                    } else {
                        image.album = Album.find.where().eq("id", requestData.get("albums")).findUnique();
                    }


                    image.user = User.authFind.ref(request().username());

                    image.save();

            }

            flash("UploadSuccess", "Upload Successful!");
            return redirect("/upload");

        } else {

            flash("error", "Missing file");
            return redirect("/upload");
        }
    }

    @Security.Authenticated(Secured.class)
    public Result profileImageUpload() {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");
        String fileExt = picture.getContentType().substring(6);
        String fileName = UUID.randomUUID().toString() + "." + fileExt;

        if (picture != null) {
            saveUploadedImage(picture, fileName);

            // Thumbnails need a height = 150px
            saveThumbnail(fileName, 150);

            deleteFullImage(fileName);

            Transaction txn = Ebean.beginTransaction();
            try {
                User savedUser = User.authFind.ref(request().username());
                if (savedUser != null) {
                    savedUser.setProfile_image(fileName);
                    savedUser.update();
                    txn.commit();
                }
            } finally {
                txn.end();
            }

            return redirect("/profileHome");

        } else {
            flash("error", "Missing file");
            return redirect("/profileHome");
        }
    }

    @Security.Authenticated(Secured.class)
    public Result wallpaperImageUpload() {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");
        String fileExt = picture.getContentType().substring(6);
        String fileName = UUID.randomUUID().toString() + "." + fileExt;

        if (picture != null) {

            saveUploadedImage(picture, fileName);

            Transaction txn = Ebean.beginTransaction();
            try {
                User savedUser = User.authFind.ref(request().username());
                if (savedUser != null) {
                    savedUser.setWall_image(fileName);
                    savedUser.update();
                    txn.commit();
                }
            } finally {
                txn.end();
            }

            return redirect("/profileHome");

        } else {
            flash("error", "Missing file");
            return redirect("/profileHome");
        }
    }

    @Security.Authenticated(Secured.class)
    public Result deleteImage(Integer id) {
        Image image = Image.findID.where().eq("id", id).findUnique();
        image.user = null;
        image.album = null;
        image.delete();
        deleteImageFiles(image.full_image);
        flash("imageDeleted", "Image Successfully Deleted!");

        return ok();
    }

    @Security.Authenticated(Secured.class)
    public Result deleteAlbum(Integer id) {
        Album album = Album.find.where().eq("id", id).findUnique();
        album.user = null;
        album.delete();
        flash("imageDeleted", "Image Successfully Deleted!");

        return ok();
    }

    public Result getThumbImage(String image) {
        //return ok(new java.io.File("/Users/bmodahl/Desktop/thumbImages/" + image));
        return ok(new java.io.File("/home/ubuntu/thumbImages/" + image));
    }

    public Result getFullImage(String image) {
        //return ok(new java.io.File("/Users/bmodahl/Desktop/fullImages/" + image));
        return ok(new java.io.File("/home/ubuntu/fullImages/" + image));
    }

    protected void saveUploadedImage(Http.MultipartFormData.FilePart<File> picture, String fileName) {

        File file = picture.getFile();

        try {

            FileUtils.moveFile(file, new File("/home/ubuntu/fullImages/", fileName));
            //FileUtils.moveFile(file, new File("/Users/bmodahl/Desktop/fullImages/", fileName));

        } catch (IOException ioe) {
            logger.error("Problem operating on filesystem");
        }


    }

    protected void saveThumbnail(String fileName, int height) {
        try (InputStream in = new URL("http://13.58.91.127:8080/1mageRes1zer/image?urls=http://52.14.60.90:8080/fullImage?image="
                + fileName + "&height=" + height).openStream()) {

            //Files.copy(in, Paths.get("/Users/bmodahl/Desktop/thumbImages/" + fileName));
            Files.copy(in, Paths.get("/home/ubuntu/thumbImages/", fileName));

        } catch (IOException ioe) {
            logger.error("Problem operating on filesystem");
        }
    }

    protected void deleteFullImage(String fileName) {
        try {

            FileUtils.forceDelete(new java.io.File("/Users/bmodahl/Desktop/fullImages/" + fileName));
            //FileUtils.forceDelete(new java.io.File("/home/ubuntu/fullImages/" + fileName));

        } catch (IOException ioe) {
            logger.error("Problem operating on filesystem");
        }
    }

    protected void deleteImageFiles(String fileName) {
        try {

            //FileUtils.forceDelete(new java.io.File("/Users/bmodahl/Desktop/fullImages/" + fileName));
            FileUtils.forceDelete(new java.io.File("/home/ubuntu/fullImages/" + fileName));

            //FileUtils.forceDelete(new java.io.File("/Users/bmodahl/Desktop/thumbnails/" + fileName));
            FileUtils.forceDelete(new java.io.File("/home/ubuntu/thumbnails/" + fileName));


        } catch (IOException ioe) {
            logger.error("Problem operating on filesystem");
        }
    }


}
