package controllers;

import com.google.inject.Inject;
import org.junit.Test;
import play.data.FormFactory;

import static org.junit.Assert.*;

/**
 * Created by bmodahl on 5/4/17.
 */
public class ImageControllerTest {

    @Inject
    private FormFactory formFactory;
    ImageController test = new ImageController(formFactory);

    @Test
    public void saveUploadedImage() throws Exception {

        // Needs FilePart from upload form
    }

    @Test
    public void saveThumbnail() throws Exception {

        test.saveThumbnail("test.jpg", 250);
        assertTrue("Image exists", new java.io.File("/Users/bmodahl/Desktop/thumbnails/test.jpg") != null);

    }

    @Test
    public void deleteFullImage() throws Exception {

        test.deleteFullImage("test.jpg");
        assertTrue("Image gone", new java.io.File("/Users/bmodahl/Desktop/thumbnails/test.jpg") == null);

    }

    @Test
    public void deleteImageFiles() throws Exception {

        test.deleteImageFiles("test.jpg");
        assertTrue("Image gone", new java.io.File("/Users/bmodahl/Desktop/thumbnails/test.jpg") == null);
    }

}