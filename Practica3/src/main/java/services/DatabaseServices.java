package services;

import domain.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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

            UsuarioServices.getInstance().update(new Usuario("danieldmw2", "Daniel", "Perez Martinez", "palomo123", true, false));
            UsuarioServices.getInstance().delete(new Usuario("arielsalcepk", "Ariel", "Salce", "palomo123", false, false));

            ArrayList<Object> users = UsuarioServices.getInstance().select();
            for (int i = 0; i < users.size(); i++)
                System.out.println(((Usuario) users.get(i)).toString());

            System.out.println(((Usuario)UsuarioServices.getInstance().selectByID("wendolapm")).toString());

            return "Done Deal";
        }));

    }



}
