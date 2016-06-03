package main; /**
 * Created by Daniel's Laptop on 5/29/2016.
 */

import domain.Articulo;
import domain.Comentario;
import domain.Etiqueta;
import domain.Usuario;
import services.*;
import spark.ModelAndView;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;
//import spark.debug.

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main
{
    public static Usuario loggedInUser = null;
    public static String login = "Iniciar Sesión";

    public static void main(String[] args)
    {
        staticFiles.location("/public");

        // Add this line to your project to enable the debug screen
        enableDebugScreen();

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        Filtros.aplicarFiltros();
        ZonaAdmin.crearZonaAdmin(freeMarker);

        //Pagina principal de la aplicacion, muestra los diferentes articulos de los mas recientes a los mas antiguos
        //Solo se muestran 70 caracteres del texto (excluyendo el titulo del articulo), y se incluye un enlace para el post completo
        //Se visualizan todas las etiquetas del articulo
        get("/home", (req, res) ->
        {
            HashMap<String, Object> map = null;
            try
            {
                ArrayList<Object> articulos = ArticuloServices.getInstance().select();
                map = new HashMap<>();
                map.put("articulos", articulos);
                map.put("title", "Let's Blog a bit!");
                map.put("home", "Home");
                map.put("registro", "¡Regístrate!");
                map.put("iniciarSesion", login);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            ModelAndView mv = new ModelAndView(map, "home.ftl");
            return mv;
        }, freeMarker);


        //Articulo mostrandose
        get("/post", (req, res) ->
        {
            HashMap<String, Object> map = null;
            try {

                String id = req.queryParams("readmore");

                Articulo a  = (Articulo)ArticuloServices.getInstance().selectByID(Integer.parseInt(id));
                ArrayList<Comentario> listComentarios = ComentarioServices.getInstance().select(a);

                if (a != null) {
                    map = new HashMap<>();
                    map.put("title", "Article");
                    map.put("postTitle", a.getTitulo());
                    map.put("autor", a.getAutor().getUsername());
                    map.put("tags", a.getListaEtiquetas());
                    map.put("fecha", a.getFecha());
                    map.put("contenido", a.getCuerpo());
                    map.put("comentarios", listComentarios);
                    map.put("home", "Home");
                    map.put("registro", "¡Regístrate!");
                    map.put("iniciarSesion", login);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return new ModelAndView(map,"post.ftl");
        }, freeMarker);

        post("/registrationPost", (req, res) ->
        {
            boolean esAutor = false;

            if(req.queryParams("autor") != null)
                esAutor = true;

            Usuario usuario = new Usuario(req.queryParams("username"), req.queryParams("name"),
                    req.queryParams("apellidos"), req.queryParams("password"), false, esAutor);
            UsuarioServices.getInstance().insert(usuario);

            loggedInUser = usuario;

            req.session().attribute("usuario", usuario);
            res.redirect("/home");
            return modelAndView(null, "");
        });

        //Es la interfaz que se utiliza para crear articulos
        get("/articleCreation", (req, res) ->
        {
            HashMap<String, Object> map = null;
            try
            {
                map = new HashMap<String, Object>();
                map.put("title", "Article");
                map.put("home", "Home");
                map.put("registro", "¡Regístrate!");
                map.put("iniciarSesion", login);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return new ModelAndView(map,"articleCreation.ftl");
        }, freeMarker);


        //Se utiliza para insertar los articulos
        post("/articleCreationPost", (req, res) ->
        {
            String[] sa = req.queryParams("etiquetas").split(",");
            ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
            ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
            for(int i = 0; i<sa.length; i++)
            {
                etiquetas.add(new Etiqueta(i, sa[i]));
            }

            try
            {
                ArticuloServices.getInstance().insert(new Articulo(1, req.queryParams("titulo"),req.queryParams("cuerpo"),loggedInUser, new Date(), comentarios,etiquetas));
            } catch(Exception e)
            {
                e.printStackTrace();
            }

            res.redirect("/home");
            return new ModelAndView(null,"");
        }, freeMarker);


        get("/login", (req, res) ->
        {
            loggedInUser = null;
            req.session().attribute("usuario", loggedInUser);
            login = "Iniciar Sesión";

            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "Article");
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", login);
            return new ModelAndView(map,"login.ftl");
        }, freeMarker);

        post("/loginPost", (req, res) ->
        {
            System.out.println(req.queryParams("username"));
            HashMap<String, Object> map = new HashMap<String, Object>();
            loggedInUser = (Usuario)UsuarioServices.getInstance().selectByID(req.queryParams("username"));
            req.session(true).attribute("usuario", loggedInUser);
            res.cookie("user", loggedInUser.getUsername()); // TODO Donde se haga el logout esto debe ser eliminado o ser igualado a nada

            login = "Cerrar Sesión";
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", login);

            res.redirect("/home");
            return new ModelAndView(null, "");
        }, freeMarker);

        get("/registration", (req, res)->
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "Article");
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", login);
            return new ModelAndView(map, "registration.ftl");
        }, freeMarker);

    }
}
