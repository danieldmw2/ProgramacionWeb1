/**
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

import java.lang.reflect.Array;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.*;

public class Main
{
    public static void main(String[] args)
    {
        staticFiles.location("/public");

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        //(long id, String titulo, String cuerpo, Usuario autor, Date fecha, ArrayList<Comentario> listaComentarios, ArrayList<Etiqueta> listaEtiquetas)
        ArrayList<Comentario> listaComentarios = new ArrayList<>();
        listaComentarios.add(new Comentario(1, "Don't give up in life man! You shall find it some day.", new Usuario("manuelespaleta", "Manuel", "Saleta", "1234", false, false), new Articulo(1, "Hi I am alive.", "I traveled around the world looking for the perfect cake and found nothing but desperation and hatred.", new Usuario("arielsalcepk", "Ariel", "Salce", "1234", false, true),new Date(), listaComentarios, listaEtiquetas)));
        listaComentarios.add(new Comentario(1, "Don't give up in life man! You shall find it some day.", new Usuario("manuelespaleta", "Manuel", "Saleta", "1234", false, false), new Articulo(1, "Hi I am alive.", "I traveled around the world looking for the perfect cake and found nothing but desperation and hatred.", new Usuario("arielsalcepk", "Ariel", "Salce", "1234", false, true),new Date(), listaComentarios, listaEtiquetas)));

        ArticuloServices.getInstance().insert(new Articulo(1, "Hi I am alive.", "I traveled around the world looking for the perfect cake and found nothing but desperation and hatred.", new Usuario("arielsalcepk", "Ariel", "Salce", "1234", false, true),new Date(), listaComentarios, listaEtiquetas));

        //Pagina principal de la aplicacion, muestra los diferentes articulos de los mas recientes a los mas antiguos
        //Solo se muestran 70 caracteres del texto (excluyendo el titulo del articulo), y se incluye un enlace para el post completo
        //Se visualizan todas las etiquetas del articulo
        get("/home", (req, res) ->
        {
            List<Object> list = new ArrayList<>();
            list.add(new Object());
            list.add(new Object());
            HashMap<String, Object> map = new HashMap<>();
            map.put("items", list);
            map.put("title", "Let's Blog a bit!");
            map.put("intro", "Computer Science's hideout");
            map.put("user", "Ariel Salce");
            map.put("date", "May 31, 2016");
            map.put("summary", "So delicious like I can eat you up every day all day and never dislike your taste you damn yum yum babe.");
            map.put("tag", "blogs");
            map.put("blogs", "unstyled");
            return new ModelAndView(map, "home.ftl");
        }, freeMarker);


        get("/post", (req, res) ->
        {
            List<Object> list = new ArrayList<>();
            list.add(new Object());
            list.add(new Object());
            HashMap<String, Object> map = new HashMap<>();
            map.put("intro", "Computer Science's hideout");
            map.put("title", "Article");
            map.put("items", list);
            map.put("postTitle", "Hello my friends, I am dying.");
            map.put("user", "Ariel Salce");
            map.put("tag", "blogs");
            map.put("blogs", "unstyled");
            return new ModelAndView(map,"post.ftl");
        }, freeMarker);

        get("/login", (req, res) -> {
            List<Object> list = new ArrayList<>();
            list.add(new Object());
            list.add(new Object());
            HashMap<String, Object> map = new HashMap<>();
            map.put("intro", "Computer Science's hideout");
            map.put("title", "Article");
            map.put("items", list);
            map.put("postTitle", "Hello my friends, I am dying.");
            map.put("user", "Ariel Salce");
            return new ModelAndView(map,"login.ftl");
        }, freeMarker);

        get("/registration", (req, res)->
        {
            List<Object> list = new ArrayList<>();
            list.add(new Object());
            list.add(new Object());
            HashMap<String, Object> map = new HashMap<>();
            map.put("intro", "Computer Science's hideout");
            map.put("title", "Article");
            map.put("items", list);
            map.put("postTitle", "Hello my friends, I am dying.");
            map.put("user", "Ariel Salce");
            return new ModelAndView(map, "registration.ftl");
        }, freeMarker);

    }
}
