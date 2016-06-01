package services;

import domain.Articulo;
import domain.Comentario;
import domain.Etiqueta;
import domain.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
