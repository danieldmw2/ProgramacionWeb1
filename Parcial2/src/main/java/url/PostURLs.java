package url;

import domain.*;
import services.AlbumServices;
import services.ComentarioServices;
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.post;
import static spark.Spark.redirect;

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

            loggedInUser = newUser;

            response.redirect("/home");
            return null;
        });

        post("/insertAlbum", (request, response) -> {
            Album album = new Album();

            album.setUsuario(loggedInUser);
            album.setDescripcion(request.queryParams("description"));

            System.out.print(request.queryParams("tags"));
            System.out.print(request.queryParams("image-0"));
            for(String tag : request.queryParams("tags").split(","))
                album.getListaEtiquetas().add(new Etiqueta(tag));

            for (int i = 1; i < 11; i++)
                if (request.queryParams("image-" + i) != null)
                    album.getImages().add(new Image(request.queryParams("image-" + i), album));

            AlbumServices.getInstance().insert(album);

            response.redirect("/home");
            return null;
        });

        post("/editAlbum", (request, response) -> {
            Album album = AlbumServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            album.setDescripcion(request.queryParams("description"));
            album.setListaEtiquetas(new ArrayList<>());

            for(String tag : request.queryParams("tags").split(","))
                album.getListaEtiquetas().add(new Etiqueta(tag));

            AlbumServices.getInstance().update(album);

            response.redirect("/home");
            return null;
        });

        post("/deleteAlbum", (request, response) -> {
            Album album = AlbumServices.getInstance().selectByID(request.queryParams("id"));
            AlbumServices.getInstance().delete(album);

            response.redirect("/home");
            return null;
        });

        post("/zonaAdmin/deleteUser", (request, response) -> {
            Usuario user = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            UsuarioServices.getInstance().delete(user);

            response.redirect("/home");
            return null;
        });

        post("/zonaAdmin/editUser", (request, response) -> {
            Usuario user = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            user.setAdministrator(true);

            UsuarioServices.getInstance().update(user);

            response.redirect("/home");
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
            return null;
        });

        post("/insertComment", (request, response) -> {
            Album album = AlbumServices.getInstance().selectByID(Long.parseLong(request.queryParams("idAlbum")));

            Comentario c = new Comentario();
            c.setAlbum(album);
            c.setAutor(loggedInUser);
            c.setComentario(request.queryParams("comentario"));

            ComentarioServices.getInstance().insert(c);

            response.redirect("/album/" + request.queryParams("idAlbum"));
            return null;
        });

        post("/editComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            c.setComentario(request.queryParams("comentario"));

            ComentarioServices.getInstance().update(c);
            response.redirect("/album/" + request.queryParams("idAlbum"));
            return null;
        });

        post("/deleteComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            ComentarioServices.getInstance().delete(c);

            response.redirect("/album/" + request.queryParams("idAlbum"));
            return null;
        });

        post("/likeComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));

            if(request.queryParams("like") != null)
                c.addLike(loggedInUser);
            else
                c.addDislike(loggedInUser);

            ComentarioServices.getInstance().update(c);
            response.redirect("/album/" + request.queryParams("idAlbum"));
            return null;
        });

        post("/likeAlbum", (request, response) -> {
            Album album = AlbumServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));

            if(request.queryParams("like") != null)
                album.addLike(loggedInUser);
            else
                album.addDislike(loggedInUser);

            AlbumServices.getInstance().update(album);
            response.redirect("/album/" + request.queryParams("id"));
            return null;
        });

    }
}
