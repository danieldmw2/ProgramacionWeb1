package main;

/**
 * Created by Daniel's Laptop on 6/2/2016.
 */
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

import static spark.Spark.*;

public class ZonaAdmin
{
    public void crearZonaAdmin(FreeMarkerEngine freeMarker)
    {
        get("/zonaAdmin/users", (request, response) -> {

            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("users", UsuarioServices.getInstance().select());
            map.put("title", "Let's Blog a bit!");
            map.put("house", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("login", "Iniciar Sesión");
            return new ModelAndView(null, "ListarUsers.ftl");
        }, freeMarker);

        get("/zonaAdmin/deleteUser", (request, response) -> {
            UsuarioServices.getInstance().delete(UsuarioServices.getInstance().selectByID(request.queryParams("id")));
            response.redirect("/zonaAdmin/users");
            return null;
        }, freeMarker);
    }
}
