package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static void main(String[] args)
    {
        get("/", ((request, response) -> {
            ArticuloServices.getInstance().createTable();
            return null;
        }));
    }



}
