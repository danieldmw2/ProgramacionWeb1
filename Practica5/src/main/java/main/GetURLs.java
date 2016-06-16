package main;

import domain.Articulo;
import domain.Etiqueta;
import services.ArticuloServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.get;

/**
 * Created by Daniel's Laptop on 6/16/2016.
 */
public class GetURLs
{
    public static void create(FreeMarkerEngine freeMarker)
    {
        get("/home", (req, res) ->
        {
            HashMap<String, Object> map = null;
            try
            {

                List<Articulo> articulos = ArticuloServices.getInstance().select();
                Collections.sort(articulos, (Object a, Object b)->((Articulo)a).getFecha().compareTo(((Articulo)b).getFecha()));
                Collections.reverse(articulos);

                int page = req.queryParams("p") != null ? Integer.parseInt(req.queryParams("p")) : 1;
                ArrayList<Articulo> articles = new ArrayList<Articulo>();
                for (int i = 0; i < 5; i++)
                {
                    int index = i + ((page - 1) * 5);

                    if (index < articulos.size())
                        articles.add(articulos.get(index));
                    else
                        break;
                }

                map = new HashMap<>();
                map.put("articulos", articles);
                map.put("title", "Let's Blog a bit!");
                map.put("home", "Home");
                map.put("registro", "¡Regístrate!");
                map.put("iniciarSesion", login);
                map.put("page", (page + 1));

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
                Articulo a  = ArticuloServices.getInstance().selectByID(Long.parseLong(id));

                if (a != null) {
                    map = new HashMap<>();
                    map.put("title", "Article");
                    map.put("readmore", id);
                    map.put("postTitle", a.getTitulo());
                    map.put("autor", a.getAutor().getUsername());
                    map.put("tags", a.getListaEtiquetas());
                    map.put("fecha", a.getFecha());
                    map.put("contenido", a.getCuerpo());
                    map.put("likearticulo", a.getLikes());
                    map.put("dislikearticulo", a.getDislikes());
                    map.put("comentarios", a.getListaComentarios());
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

        get("/registration", (req, res)->
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "Article");
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", login);
            return new ModelAndView(map, "registration.ftl");
        }, freeMarker);

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

        get("/logout", (req, res) ->
        {

            HashMap<String, Object> map = new HashMap<String, Object>();

            login = "Iniciar Sesión";

            map.put("usuario", loggedInUser.getUsername());
            req.session(true).attribute("usuario", null);
            res.cookie("user", "");
            loggedInUser = null;
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", login);
            res.redirect("/home");
            return new ModelAndView(null,"");
        },freeMarker);


        get("/modificarArticulo", (req, res) ->
        {
            String id = req.queryParams("modificarArticulo");
            Articulo a = ArticuloServices.getInstance().selectByID(Long.parseLong(id));
            System.out.println(a.getAutor().getUsername());
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "Article");
            map.put("home", "Home");
            map.put("registro", "¡Regístrate!");
            map.put("iniciarSesion", login);
            map.put("titulo", a.getTitulo());
            map.put("cuerpo", a.getCuerpo());
            map.put("id", a.getId());

            String s = "";
            for(int i = 0; i<a.getListaEtiquetas().size(); i++)
            {
                if(i == (a.getListaEtiquetas().size() - 1))
                    s += a.getListaEtiquetas().get(i).getEtiqueta();
                else
                    s += a.getListaEtiquetas().get(i).getEtiqueta() + ",";
            }
            map.put("etiquetas", s);

            return new ModelAndView(map,"modificarArticulo.ftl");
        }, freeMarker);

        get("/", (request, response) -> {
            response.cookie("user", "");
            request.session(true).attribute("usuario", null);
            login = "Iniciar Sesión";
            loggedInUser = null;
            response.redirect("/home");
            return null;
        });

        //Para mostrar los articulos cuando se cliquea una etiqueta especifica.
        get("/mostrarArticulosEtiqueta", (request, response) -> {

            String id = request.queryParams("idetiqueta");
            ArrayList<Articulo> listaFinal = new ArrayList<Articulo>();
            List<Articulo> lista = ArticuloServices.getInstance().select();
            HashMap<String, Object> map = new HashMap<>();
            for(Articulo a: lista)
            {
                for(Etiqueta e: a.getListaEtiquetas())
                {
                    if(e.getEtiqueta().toLowerCase().equals(id.toLowerCase()))
                    {
                        listaFinal.add(a);
                    }
                }
            }

            map.put("articulos", listaFinal);
            map.put("iniciarSesion", login);
            //System.out.println(listaFinal.size() + " Nombres " + listaFinal.get(0) + ", " + listaFinal.get(1));
            return new ModelAndView(map,"articulosPorEtiqueta.ftl");
        }, freeMarker);

    }
}
