package services;

import domain.Articulo;
import domain.Comentario;
import domain.Etiqueta;
import domain.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static spark.Spark.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public abstract class DatabaseServices
{
    protected Connection getConnection()
    {
        Connection toReturn = null;
        try
        {
            toReturn = DriverManager.getConnection("jdbc:h2:~/practica3", "user", "admin");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return toReturn;
    }

    protected abstract boolean createTable();
    protected abstract ArrayList<Object> select();
    protected abstract Object selectByID(Object o);
    protected abstract boolean insert(Object o);
    protected abstract boolean update(Object o);
    protected abstract boolean delete(Object o);

    public static void main(String[] args)
    {
        get("/", ((request, response) -> {
            ArticuloServices.getInstance().createTable();
            return null;
        }));

        get("/home", ((request, response) -> {
            UsuarioServices.getInstance().insert(new Usuario("danieldmw2", "Daniel", "Perez", "palomo123", true, false));
            UsuarioServices.getInstance().insert(new Usuario("manuelrpm", "Manuel", "Perez", "palomo123", false, true));
            UsuarioServices.getInstance().insert(new Usuario("wendolapm", "Wendy", "Perez", "palomo123", true, true));
            UsuarioServices.getInstance().insert(new Usuario("arielsalcepk", "Ariel", "Salce", "palomo123", false, false));

            ArrayList<Object> users = UsuarioServices.getInstance().select();
            for (int i = 0; i < users.size(); i++)
                System.out.println(((Usuario) users.get(i)).toString());

            System.out.println(((Usuario)UsuarioServices.getInstance().selectByID("wendolapm")).toString());

            return "Done Deal";
        }));

        get("/gg", (request, response) -> {

            Comentario c = new Comentario(3,
                    "Super post",
                    (Usuario) UsuarioServices.getInstance().selectByID("arielsalcepk"),
                    (Articulo) ArticuloServices.getInstance().selectByID(1));

            ComentarioServices.getInstance().insertComment(c, c.getArticulo());

            Etiqueta e = (Etiqueta) EtiquetaServices.getInstance().selectByID(3);
            EtiquetaServices.getInstance().insertTag(e, (Articulo) ArticuloServices.getInstance().selectByID(1));

            return "Done Deal 3";
        });

        get("/ggg", (request, response) -> {

            Articulo a = (Articulo) ArticuloServices.getInstance().selectByID(1);
            a.setCuerpo(
                    "Lorem ipsum dolor sit amet, no eum vero quodsi " +
                    "torquatos. Mei eu nominati urbanitas voluptatum. Nibh elitr " +
                    "iisque ea mei, id duo omnes homero. Eos ne corpora argumentum, " +
                    "cum in viris splendide liberavisse." +
                    "Eum novum mandamus ea, cu diam reprimique consectetuer mei. " +
                    "Libris vivendum ne mel, ad ridens apeirian mei, modo " +
                    "periculis eum et. Pri quod esse dolorum ut, ei meliore " +
                    "facilis eos. Vel facer nominavi deserunt an, eos an " +
                    "docendi voluptatum posidonium. Sea patrioque sententiae " +
                    "at, debet epicuri eloquentiam nec eu, eu sed dolor ubique " +
                    "vituperatoribus. Ne ignota iisque torquatos has, in pri hinc " +
                    "dolores reformidans. Putant urbanitas conclusionemque his no, an " +
                    "aliquid platonem complectitur his, qui in cibo fabellas. Labore " +
                    "melius ut cum, aeterno suscipiantur cum cu. Eos sale putant virtute et. " +
                    "Sumo mentitum cu qui, veri volutpat inciderint cu vim. Te vis minim nonumy platonem." +
                    "Pro utamur scripta vocibus ad, lorem eripuit perfecto sea ea. Pro volumus epicuri " +
                    "invenire ei. Id scripta volumus deserunt ius, ea est affert consul vivendum, " +
                    "clita integre recteque et duo. Cu lorem inimicus quo, lucilius constituto " +
                    "argumentum vis ex. Nulla impetus scriptorem an vis. Id qui sumo disputationi, " +
                    "alia possit mentitum qui eu. Dictas prodesset ne his, his at alii denique voluptatibus. " +
                    "Vim option impedit singulis et, mea id vocibus accumsan signiferumque. Ne falli partem ius," +
                    " mea ex tantas putant antiopam.");

            ArticuloServices.getInstance().update(a);

            return "Done Deal 4";
        });

        get("/test", ((request, response) -> {

            EtiquetaServices.getInstance().insert(new Etiqueta(1, "Java"));
            EtiquetaServices.getInstance().insert(new Etiqueta(2, "C"));
            EtiquetaServices.getInstance().insert(new Etiqueta(3, "C++"));
            EtiquetaServices.getInstance().insert(new Etiqueta(4, "Super"));
            EtiquetaServices.getInstance().insert(new Etiqueta(5, "Gabriela"));
            EtiquetaServices.getInstance().insert(new Etiqueta(6, "C#"));
            EtiquetaServices.getInstance().insert(new Etiqueta(7, "F#"));

            ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
            etiquetas.add(new Etiqueta(1, "Java"));
            etiquetas.add(new Etiqueta(5, "Gabriela"));


            Articulo a = new Articulo(
                    1,
                    "Hola todos, hoy me muero",
                    "Siiii mainin, bye bye",
                    (Usuario) UsuarioServices.getInstance().selectByID("danieldmw2"),
                    new Date(),
                    new ArrayList<Comentario>(),
                    etiquetas);

            ArticuloServices.getInstance().insert(a);

            return "Done Deal 2";
        }));

    }



}
