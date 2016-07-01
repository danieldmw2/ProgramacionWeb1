package url;

import domain.*;
import services.ComentarioServices;
import services.ImageServices;
import services.UsuarioServices;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.post;

/**
 * Created by Daniel's Laptop on 6/25/2016.
 */
public class PostURLs
{
    public static void create(FreeMarkerEngine freeMarker)
    {
        post("/insertUser", (request, response) -> {
            Usuario newUser = new Usuario();

            newUser.setUsername(request.queryParams("username"));
            newUser.setEmail(request.queryParams("email"));
            newUser.setPassword(request.queryParams("password"));
            newUser.setAdministrator(false);

            UsuarioServices.getInstance().insert(newUser);

            response.redirect("/home");
            return null;
        });

        post("/insertImage", (request, response) -> {
            System.out.println(request.queryParams("image"));
            Image image = new Image("C:\\" + request.queryParams("image"), request.queryParams("description"), request.queryParams("title"),loggedInUser);

            for(String tag : request.queryParams("tags").split(","))
                image.getListaEtiquetas().add(new Etiqueta(tag));

            ImageServices.getInstance().insert(image);
            response.redirect("/home");
            return null;
        });

        post("/editImage", (request, response) -> {
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            image.setDescripcion(request.queryParams("description"));
            image.setListaEtiquetas(new ArrayList<>());

            for(String tag : request.queryParams("tags").split(","))
                image.getListaEtiquetas().add(new Etiqueta(tag));

            ImageServices.getInstance().update(image);

            response.redirect("/home");
            return null;
        });

        post("/deleteImage", (request, response) -> {
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            ImageServices.getInstance().delete(image);

            response.redirect("/home");
            return null;
        });

        post("/zonaAdmin/deleteUser", (request, response) -> {
            Usuario user = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            UsuarioServices.getInstance().delete(user);

            response.redirect("/zonaAdmin");
            return null;
        });

        post("/zonaAdmin/editUser", (request, response) -> {
            Usuario user = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            user.setAdministrator(true);

            UsuarioServices.getInstance().update(user);

            response.redirect("/zonaAdmin");
            return null;
        });

        post("/login", (request, response) -> {
            loggedInUser = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            System.out.println(loggedInUser);
            if (loggedInUser.getPassword().equals(request.queryParams("password")))
            {
                request.session(true).attribute("usuario", loggedInUser);
                response.cookie("user", loggedInUser.getUsername());
                login = "Cerrar Sesión";
            }
            else
                loggedInUser = null;

            response.redirect("/home");
            return null;
        });

        post("/logout", (request, response) -> {
            login = "Iniciar Sesión";

            request.session(true).attribute("usuario", null);
            response.cookie("user", "");
            loggedInUser = null;
            response.redirect("/home");
            return null;
        });

        post("/insertComment", (request, response) -> {
            Image album = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("idImage")));

            Comentario c = new Comentario();
            c.setImage(album);
            c.setAutor(loggedInUser);
            c.setComentario(request.queryParams("comentario"));

            ComentarioServices.getInstance().insert(c);

            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/editComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            c.setComentario(request.queryParams("comentario"));

            ComentarioServices.getInstance().update(c);
            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/deleteComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            ComentarioServices.getInstance().delete(c);

            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/likeComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            c.addLike(loggedInUser);

            ComentarioServices.getInstance().update(c);
            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/dislikeComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            c.addDislike(loggedInUser);

            ComentarioServices.getInstance().update(c);
            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/likeImage", (request, response) -> {
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            image.addLike(loggedInUser);

            ImageServices.getInstance().update(image);
            response.redirect("/image/" + request.queryParams("id"));
            return null;
        });

        post("/dislikeImage", (request, response) -> {
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            image.addDislike(loggedInUser);

            ImageServices.getInstance().update(image);
            response.redirect("/image/" + request.queryParams("id"));
            return null;
        });

    }
}
