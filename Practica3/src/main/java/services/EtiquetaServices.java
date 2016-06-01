package services;

import domain.Articulo;
import domain.Etiqueta;
import domain.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class EtiquetaServices extends DatabaseServices
{
    private EtiquetaServices()
    {
        super();
    }

    private static EtiquetaServices instance = null;

    public static EtiquetaServices getInstance()
    {
        if(instance == null)
            instance = new EtiquetaServices();

        return instance;
    }

    public boolean createTable()
    {
        String statement = "CREATE TABLE ETIQUETAS\n" +
                "(\n" +
                "  ID       INTEGER,\n" +
                "  ETIQUETA VARCHAR(50) NOT NULL,\n" +
                "  PRIMARY KEY (ID)\n" +
                ");";

        try
        {
            getConnection().createStatement().execute(statement);
            statement = "CREATE TABLE ARTICULO_ETIQUETA\n" +
                    "(\n" +
                    "  ID_ARTICULO INTEGER,\n" +
                    "  ID_ETIQUETA INTEGER,\n" +
                    "  PRIMARY KEY (ID_ETIQUETA, ID_ARTICULO),\n" +
                    "  FOREIGN KEY (ID_ARTICULO) REFERENCES ARTICULOS (ID),\n" +
                    "  FOREIGN KEY (ID_ETIQUETA) REFERENCES ETIQUETAS (ID)\n" +
                    ");";
            getConnection().createStatement().execute(statement);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    protected ArrayList<Object> select()
    {
        Connection con = null;
        ArrayList<Object> toReturn = new ArrayList<Object>();
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM ETIQUETAS");

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                toReturn.add(
                        new Etiqueta(rs.getLong("id"), rs.getString("etiqueta"))
                        );
            }

            con.close();
        }
        catch (SQLException e)
        {
            return null;
        }

        return toReturn;
    }

    protected ArrayList<Etiqueta> select(long articuloID)
    {
        Connection con = null;
        ArrayList<Etiqueta> toReturn = new ArrayList<Etiqueta>();
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT E.ETIQUETA, E.ID " +
                    "FROM ARTICULO_ETIQUETA AE, ETIQUETA E " +
                    "WHERE AE.ID_ARTICULO = ? AND E.ID = AE.ID_ETIQUETA");
            ps.setLong(1, articuloID);

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                toReturn.add(
                        new Etiqueta(rs.getLong(2), rs.getString(1))
                );
            }

            con.close();
        }
        catch (SQLException e)
        {
            return null;
        }

        return toReturn;
    }

    protected Object selectByID(Object o)
    {
        Connection con = null;
        Etiqueta toReturn = null;
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM ETIQUETAS WHERE ID = ?");
            ps.setInt(1, (Integer) o);

            ResultSet rs = ps.executeQuery();
            rs.next();

            toReturn =  new Etiqueta(rs.getLong("id"), rs.getString("etiqueta"));

            con.close();
        }
        catch (SQLException e)
        {
            return null;
        }

        return toReturn;
    }

    protected boolean insert(Object o)
    {
        if(!(o instanceof Etiqueta))
            return false;

        Connection con = null;
        try
        {
            Etiqueta u = (Etiqueta) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("INSERT INTO ETIQUETAS VALUES(?, ?)");
            ps.setLong(1, u.getId());
            ps.setString(2, u.getEtiqueta());

            ps.execute();

            con.close();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    protected boolean insertTag(Etiqueta e, Articulo a)
    {
        Connection con = null;

        try
        {
            con = getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("INSERT INTO ARTICULO_ETIQUETA VALUES(?, ?)");
            ps.setLong(1, a.getId());
            ps.setLong(2, e.getId());

            ps.execute();
        }
        catch (SQLException ex)
        {
            return false;
        }

        return true;
    }

    protected boolean update(Object o)
    {
        if(!(o instanceof Etiqueta))
            return false;

        Connection con = null;
        try
        {
            Etiqueta u = (Etiqueta) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("UPDATE ETIQUETAS SET ID = ?, ETIQUETA = ? WHERE ID = ?");
            ps.setLong(1, u.getId());
            ps.setString(2, u.getEtiqueta());
            ps.setLong(3, u.getId());

            ps.execute();

            con.close();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    protected boolean delete(Object o)
    {
        if(!(o instanceof Etiqueta))
            return false;

        Connection con = null;
        try
        {
            Etiqueta u = (Etiqueta) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("DELETE FROM ETIQUETAS WHERE ID = ?");
            ps.setLong(1, u.getId());

            ps.execute();

            con.close();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }
}
