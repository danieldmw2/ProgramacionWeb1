package url;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.get;

import domain.*;
import services.AlbumServices;
import services.ComentarioServices;
import services.ImageServices;
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.imageio.ImageIO;
import javax.persistence.Query;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by Daniel's Laptop on 6/25/2016.
 */
public class GetURLs
{
    public static void create(FreeMarkerEngine freeMarker)
    {
        // localhost;4567/album/"AlbumID" or localhost;4567/album/"AlbumID"/"Image Filename"
        // Max 10 Images per Album
        get("/album/*", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();

            String[] url = request.url().split("/");

            Album album = AlbumServices.getInstance().selectByID(Long.parseLong(url[4]));
            model.put("comentarios", album.getListaComentarios());
            model.put("etiquetas", album.getListaEtiquetas());
            model.put("album", album);
            model.put("iniciarSesion", "Login");

            if(url.length > 5)
            {
                Image image = new Image();

                for(Image i : album.getImages())
                    if(i.getFilename().equals(url[5]))
                        image = i;

                image.addView();
                ImageServices.getInstance().update(image);

                model.put("image", image);
                return new ModelAndView(model, "images.ftl"); // TODO FTL
            }
            HashSet<Image> hs = new HashSet<>(album.getImages());

            album.addView();
            AlbumServices.getInstance().update(album);

            model.put("images", hs);
            return new ModelAndView(model, "album.ftl"); // TODO FTL
        }, freeMarker);

        get("/home", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "test.ftl");
        }, freeMarker);

        get("/upload", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "test.ftl");
        }, freeMarker);

        get("/edit", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            Album album = AlbumServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            model.put("album", album);
            return new ModelAndView(model, "test.ftl");
        }, freeMarker);

        get("/sign-up", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "test.ftl");
        }, freeMarker);

        get("/sign-in", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            loggedInUser = null;
            request.session().attribute("usuario", loggedInUser);
            login = "Iniciar Sesión";

            return new ModelAndView(model, "test.ftl");
        }, freeMarker);

        get("/zonaAdmin", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("users", UsuarioServices.getInstance().select());
            return new ModelAndView(model, "test.ftl");
        }, freeMarker);
    }
}
