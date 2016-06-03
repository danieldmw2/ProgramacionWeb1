package services;

import domain.Articulo;
import domain.Comentario;
import domain.Etiqueta;
import domain.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public static void cleanUp()
    {
        Connection con = null;
        try
        {
            con = DriverManager.getConnection("jdbc:h2:~/practica3", "user", "admin");

            PreparedStatement ps = con.prepareStatement("DELETE FROM ARTICULO_COMENTARIO");
            ps.execute();
            ps = con.prepareStatement("DELETE FROM COMENTARIOS");
            ps.execute();
            ps = con.prepareStatement("DELETE FROM ARTICULO_ETIQUETA");
            ps.execute();
            ps = con.prepareStatement("DELETE FROM ETIQUETAS");
            ps.execute();
            ps = con.prepareStatement("DELETE FROM ARTICULOS");
            ps.execute();
            ps = con.prepareStatement("DELETE FROM USUARIOS");
            ps.execute();
            ps = con.prepareStatement("INSERT INTO USUARIOS VALUES('USER', 'USER', 'USER', 'ADMIN', 'T', 'T')");
            ps.execute();
            ps = con.prepareStatement("UPDATE IDENTIFICADORES SET ID_ARTICULO = 1, ID_ETIQUETA = 1, ID_COMENTARIO = 1");
            ps.execute();

            con.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected abstract boolean createTable();
    protected abstract ArrayList<Object> select();
    protected abstract Object selectByID(Object o);
    protected abstract boolean insert(Object o);
    protected abstract boolean update(Object o);
    protected abstract boolean delete(Object o);
}
