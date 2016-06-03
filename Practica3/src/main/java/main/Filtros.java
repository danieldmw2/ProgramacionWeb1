package main;

import domain.Articulo;
import domain.Usuario;
import services.ArticuloServices;
import services.UsuarioServices;

import static spark.Spark.before;
import static spark.Spark.halt;

/**
 * Created by Daniel's Laptop on 6/2/2016.
 */
public class Filtros
{
    public static void aplicarFiltros()
    {
        before("/*", (request, response) -> {
            if(Main.loggedInUser == null)
            {
                if(request.cookie("user") != null && !request.cookie("user").equals(""))
                {
                    Usuario user = (Usuario) UsuarioServices.getInstance().selectByID(request.cookie("user"));
                    request.session(true).attribute("usuario", user);
                    Main.loggedInUser = user;
                    Main.login = "Cerrar Session";
                }
            }
        });

        before("/login",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if(usuario != null)
            {
                response.redirect("/home");
            }
        });

        before("/modificarArticulo",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            Articulo art = (Articulo) ArticuloServices.getInstance().selectByID(Integer.parseInt(request.queryParams("articulo")));

            if(usuario == null)
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
            else if(!art.getAutor().getUsername().equals(usuario.getUsername()) || !usuario.isAdministrator())
                halt(401, "Tiene que ser el autor del post o administrador del sistema para poder modificarlo");
        });

        before("/borrarArticuloPost",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            Articulo art = (Articulo) ArticuloServices.getInstance().selectByID(Integer.parseInt(request.queryParams("articulo")));

            if(usuario == null)
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
            else if(!art.getAutor().getUsername().equals(usuario.getUsername()) && !usuario.isAdministrator())
                halt(401, "Tiene que ser el autor del post o administrador del sistema para poder borrarlo");
        });

        before("/articleCreation",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if(usuario == null)
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
            else if(!usuario.isAutor() && !usuario.isAdministrator())
                halt(401, "Tiene que ser autor o administrador del sistema para poder crear un articulo");
        });

        before("/agregarComentario",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if(usuario == null)
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
        });

        before("/zonaAdmin/*", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if(usuario == null)
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
            else if(!usuario.isAdministrator())
                halt(401, "Tiene que ser administrador del sistema para poder entrar a esta area");
        });

    }
}
