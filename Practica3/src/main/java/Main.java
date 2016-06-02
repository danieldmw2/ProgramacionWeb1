/**
 * Created by Daniel's Laptop on 5/29/2016.
 */

import domain.Articulo;
import domain.Comentario;
import domain.Etiqueta;
import domain.Usuario;
import freemarker.template.Template;
import services.*;
import spark.ModelAndView;
import freemarker.template.Configuration;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.*;

import static spark.Spark.*;

public class Main
{
    static Usuario loggedInUser = null;
    public static void main(String[] args)
    {
        staticFiles.location("/public");

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

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
                map.put("house", "Home");
                map.put("registro", "¡Regístrate!");
                map.put("login", "Iniciar Sesión");
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return new ModelAndView(map, "home.ftl");
        }, freeMarker);


        //Articulo mostrandose
        get("/post", (req, res) ->
        {
            HashMap<String, Object> map = null;
            try {

                String id = "";
                for (String s : req.queryParams())
                    id = s;

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
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return new ModelAndView(map,"post.ftl");
        }, freeMarker);

        post("/registrationPost", (req, res) ->
        {
            Usuario usuario = new Usuario(req.queryParams("username"), req.queryParams("name"),
                    req.queryParams("apellidos"), req.queryParams("password"), false, false);
            UsuarioServices.getInstance().insert(usuario);
            res.redirect("/home");
            return modelAndView(null, "");
        });

        get("/articleCreation", (req, res) ->
        {
            HashMap<String, Object> map = null;
            try
            {
                map = new HashMap<String, Object>();
                map.put("title", "Article");

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return new ModelAndView(map,"articleCreation.ftl");
        }, freeMarker);

        post("/articleCreationPost", (req, res) ->
        {
            String[] sa = req.queryParams("tags").split(",");
            long id = ArticuloServices.getInstance().select().size()+1;
            try
            {
                ArticuloServices.getInstance().insert(new Articulo(id, req.queryParams("titulo"),req.queryParams("cuerpo"),loggedInUser, new Date(), null, null));
            } catch(Exception e)
            {
                e.printStackTrace();
            }

            res.redirect("/home");
            return new ModelAndView(null,"");
        }, freeMarker);


        get("/login", (req, res) ->
        {
            List<Object> list = new ArrayList<>();
            list.add(new Object());
            list.add(new Object());
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "Article");
            map.put("items", list);
            map.put("postTitle", "Hello my friends, I am dying.");
            map.put("user", "Ariel Salce");
            return new ModelAndView(map,"login.ftl");
        }, freeMarker);

        post("/loginPost", (req, res) ->
        {
            if(req.queryParams("username").equals(loggedInUser.getUsername()))

            res.redirect("/home");
            return new ModelAndView(null, "");
        }, freeMarker);

        get("/registration", (req, res)->
        {
            List<Object> list = new ArrayList<>();
            list.add(new Object());
            list.add(new Object());
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "Article");
            map.put("items", list);
            map.put("user", "Ariel Salce");
            return new ModelAndView(map, "registration.ftl");
        }, freeMarker);

    }
}
