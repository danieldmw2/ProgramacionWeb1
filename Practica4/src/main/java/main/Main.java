package main;

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

import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main
{
    public static Usuario loggedInUser = null;
    public static String login = "Iniciar Sesión";
    public static int pager = 5;

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

        if(UsuarioServices.getInstance().selectByID("USER") == null)
            UsuarioServices.getInstance().insert(new Usuario("USER", "USER", "USER", "ADMIN", true, true));

        //Pagina principal de la aplicacion, muestra los diferentes articulos de los mas recientes a los mas antiguos
        //Solo se muestran 70 caracteres del texto (excluyendo el titulo del articulo), y se incluye un enlace para el post completo
        //Se visualizan todas las etiquetas del articulo
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
            res.cookie("user", loggedInUser.getUsername());
            login = "Cerrar Sesión";

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
            List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
            List<Comentario> comentarios = new ArrayList<Comentario>();
            List<Etiqueta> todas = EtiquetaServices.getInstance().select();
            ArrayList<String> aux = new ArrayList<String>();
            for(int i = 0; i<todas.size(); i++)
                aux.add(todas.get(i).getEtiqueta().toLowerCase());
            for(int i = 0; i<sa.length; i++)
            {
                Etiqueta e = new Etiqueta(sa[i]);
                if(aux.contains(e.getEtiqueta().toLowerCase()))
                    EtiquetaServices.getInstance().insert(e);

                etiquetas.add(e);
            }

            Articulo art = new Articulo(req.queryParams("titulo"),req.queryParams("cuerpo"),loggedInUser, new Date(), comentarios,etiquetas);
            try
            {
                ArticuloServices.getInstance().insert(art);
            }
            catch(Exception e)
            {
                ArticuloServices.getInstance().update(art);
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
            System.out.println(loggedInUser);
            if(loggedInUser.getPassword().equals(req.queryParams("password")))
            {
                req.session(true).attribute("usuario", loggedInUser);
                res.cookie("user", loggedInUser.getUsername());
                login = "Cerrar Sesión";
            }
            else
                loggedInUser = null;

            res.redirect("/home");
            return new ModelAndView(null, "");
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

        post("/modificarArticuloPost", (req, res) ->
        {
            String id = req.queryParams("modificarArticulo");
            System.out.println();
            String etiquetas = req.queryParams("etiquetas");
            String[] ss = etiquetas.trim().split(",");

            ArrayList<Etiqueta> ets = new ArrayList<Etiqueta>();
            for(int i = 0; i < ss.length ; i++)
                ets.add(new Etiqueta(ss[i]));

            Articulo a1 = ArticuloServices.getInstance().selectByID(Long.parseLong(id));
            a1.setCuerpo(req.queryParams("cuerpo"));
            a1.setTitulo(req.queryParams("titulo"));
            a1.setListaEtiquetas(ets);

            ArticuloServices.getInstance().update(a1);

            res.redirect("/home");
            return new ModelAndView(null,"");
        }, freeMarker);

        post("/borrarArticuloPost", (req, res) ->
        {
            System.out.println(req.queryParams("borrarArticulo"));
            Articulo a = ArticuloServices.getInstance().selectByID(Long.parseLong(req.queryParams("borrarArticulo")));

            for(Comentario c : a.getListaComentarios())
                ComentarioServices.getInstance().delete(c);

            ArticuloServices.getInstance().delete(a);

            res.redirect("/home");
            return new ModelAndView(null, "");
        }, freeMarker);

        post("/agregarComentario", (req, res) ->
        {
            String s = req.queryParams("commentcontent");
            System.out.println(s);
            Articulo a = ArticuloServices.getInstance().selectByID(Long.parseLong(req.queryParams("readmore")));

            Comentario c = new Comentario(s, loggedInUser, a);
            ComentarioServices.getInstance().insert(c);

            res.redirect("/post?readmore=" + req.queryParams("readmore"));
            return null;
        });

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

        //Aqui se le da el valor positivo o negativo al articulo
        post("/valoracionArticulo", (req, res) ->
        {
            String idArticulo = "";
            if(req.queryParams("idArticulo") != null)
            {
                idArticulo = req.queryParams("idArticulo");
                Articulo a = ArticuloServices.getInstance().selectByID(Long.parseLong(idArticulo));
                Integer i = 0;

                i = a.getLikes() + 1;
                a.setLikes(i);

                ArticuloServices.getInstance().update(a);
            }
            else
            {
                idArticulo = req.queryParams("idArticulo2");
                Articulo a = ArticuloServices.getInstance().selectByID(Long.parseLong(idArticulo));
                Integer i = 0;

                i = a.getDislikes() + 1;
                a.setDislikes(i);

                ArticuloServices.getInstance().update(a);
            }

            res.redirect("/post?readmore=" + idArticulo);
            return null;
        });

        //Aqui se tiene la valoracion positiva o negativa de los comentarios
        post("/valoracionComentario", (req, res) ->
        {
            String idComentario = "";
            if(req.queryParams("idComentario") != null)
            {
                idComentario = req.queryParams("idComentario");

                Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(idComentario));

                Integer i = 0;

                i = c.getLikes() + 1;
                c.setLikes(i);

                ComentarioServices.getInstance().update(c);

            }
            else
            {
                idComentario = req.queryParams("idComentario2");
                Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(idComentario));
                Integer i = 0;;

                i = c.getDislikes() + 1;
                c.setDislikes(i);

                ComentarioServices.getInstance().update(c);

            }

            res.redirect("/post?readmore=" + req.queryParams("readmore"));
            return null;
        });

    }
}
