package url;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.get;

import domain.*;
import services.ImageServices;
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;

/**
 * Created by Daniel's Laptop on 6/25/2016.
 */
public class GetURLs
{
    public static void create(FreeMarkerEngine freeMarker)
    {
        // localhost;4567/album/"AlbumID" or localhost;4567/album/"AlbumID"/"Image Filename"
        get("/image/*", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();

            String[] url = request.url().split("/");

            Image image = ImageServices.getInstance().selectByID(Long.parseLong(url[4]));
            model.put("comentarios", image.getListaComentarios());
            model.put("etiquetas", image.getListaEtiquetas());
            model.put("album", image);
            model.put("iniciarSesion", login);

            image.addView();
            ImageServices.getInstance().update(image);

            model.put("images", image);
            return new ModelAndView(model, "album.ftl");
        }, freeMarker);

        get("/home", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            int page = request.queryParams("p") != null ? Integer.parseInt(request.queryParams("p")) : 1;
            model.put("page", (page + 1));

            //Change this way to a more efficient way later.
            List<Image> aux = ImageServices.getInstance().select();
            List<Image> images = new ArrayList<>();

            for (int i = 0; i < 5; i++)
            {
                int index = i + ((page - 1) * 5);

                if (index < aux.size())
                    images.add(aux.get(index));
                else
                    break;
            }

            model.put("albumes", images);
            model.put("iniciarSesion", "Iniciar Sesión");
            return new ModelAndView(model, "home.ftl");
        }, freeMarker);

        get("/upload", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("iniciarSesion", "Iniciar Sesión");
            model.put("imageNumber", 0);
            return new ModelAndView(model, "createAlbum.ftl");
        }, freeMarker);

        get("/edit", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            Image album = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            model.put("album", album);
            return new ModelAndView(model, "test.ftl");
        }, freeMarker);

        get("/sign-up", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("iniciarSesion", "Iniciar Sesión");
            return new ModelAndView(model, "registration.ftl");
        }, freeMarker);

        get("/sign-in", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            loggedInUser = null;
            request.session().attribute("usuario", loggedInUser);
            login = "Iniciar Sesión";
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "login.ftl");
        }, freeMarker);

        get("/zonaAdmin", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("users", UsuarioServices.getInstance().select());
            return new ModelAndView(model, "test.ftl");
        }, freeMarker);
    }
}
