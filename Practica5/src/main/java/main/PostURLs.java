package main;

import domain.Articulo;
import domain.Comentario;
import domain.Etiqueta;
import domain.Usuario;
import services.ArticuloServices;
import services.ComentarioServices;
import services.EtiquetaServices;
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.modelAndView;
import static spark.Spark.post;

/**
 * Created by Daniel's Laptop on 6/16/2016.
 */
public class PostURLs
{
    public static void create(FreeMarkerEngine freeMarker)
    {
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
