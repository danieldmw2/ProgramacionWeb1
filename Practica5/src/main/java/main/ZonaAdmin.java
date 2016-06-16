package main;

/**
 * Created by Daniel's Laptop on 6/2/2016.
 */
import domain.Usuario;
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

import static spark.Spark.*;

public class ZonaAdmin
{
    public static void create(FreeMarkerEngine freeMarker)
    {
        get("/zonaAdmin/users", (request, response) -> {

            HashMap<String, Object> map = new HashMap<String, Object>();
            System.out.println(UsuarioServices.getInstance().select().get(0).isAutor());
            map.put("users", UsuarioServices.getInstance().select());
            map.put("title", "ListarUsuario");
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", "Cerrar Sesion");
            return new ModelAndView(map, "ListarUsers.ftl");
        }, freeMarker);

        get("/zonaAdmin/insert", (request, response) -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "Register");
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", "Cerrar Session");
            return new ModelAndView(map, "registration.ftl");
        }, freeMarker);

        post("/zonaAdmin/registrationPost", (req, res) ->
        {
            boolean esAutor = false;

            if(req.queryParams("autor") != null)
                esAutor = true;

            Usuario usuario = new Usuario(req.queryParams("username"), req.queryParams("name"),
                    req.queryParams("apellidos"), req.queryParams("password"), true, esAutor);
            UsuarioServices.getInstance().insert(usuario);

            res.redirect("/zonaAdmin/users");
            return null;
        });

        get("/zonaAdmin/deleteUser", (request, response) -> {
            System.out.println("lol");
            UsuarioServices.getInstance().delete(UsuarioServices.getInstance().selectByID(request.queryParams("id")));
            response.redirect("/zonaAdmin/users");
            return null;
        });
    }
}
