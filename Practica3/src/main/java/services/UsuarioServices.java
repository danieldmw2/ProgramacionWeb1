package services;

import domain.Articulo;
import domain.Usuario;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class UsuarioServices extends DatabaseServices
{
    private UsuarioServices()
    {
        super();
    }

    private static UsuarioServices instance = null;

    public static UsuarioServices getInstance()
    {
        if (instance == null)
            instance = new UsuarioServices();

        return instance;
    }

    public boolean createTable()
    {
        String statement = "CREATE TABLE USUARIOS\n" +
                "(\n" +
                "  USERNAME      VARCHAR(50),\n" +
                "  NOMBRE        VARCHAR(100) NOT NULL,\n" +
                "  APELLIDOS     VARCHAR(100) NOT NULL,\n" +
                "  PASSWORD      VARCHAR(50)  NOT NULL,\n" +
                "  ADMINISTRADOR CHAR         NOT NULL,\n" +
                "  AUTOR         CHAR         NOT NULL,\n" +
                "  PRIMARY KEY (USERNAME),\n" +
                "  CHECK (ADMINISTRADOR = 'T' || ADMINISTRADOR = 'F'),\n" +
                "  CHECK (AUTOR = 'T' || AUTOR = 'F')\n" +
                ");";

        try
        {
            getConnection().createStatement().execute(statement);
        } catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    public ArrayList<Object> select()
    {
        Connection con = null;
        ArrayList<Object> toReturn = new ArrayList<Object>();
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM USUARIOS");

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                toReturn.add(new Usuario(
                        rs.getString("username"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("password"),
                        rs.getString("administrador").equals("T"),
                        rs.getString("autor").equals("T")));
            }


            con.close();
        } catch (SQLException e)
        {
            return null;
        }

        return toReturn;
    }

    public Object selectByID(Object o)
    {
        Connection con = null;
        Usuario toReturn = null;
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM USUARIOS WHERE LOWER (USERNAME) = LOWER (?)");
            ps.setString(1, o.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            toReturn = new Usuario(
                    rs.getString("username"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("password"),
                    rs.getString("administrador").equals("T"),
                    rs.getString("autor").equals("T"));

            con.close();
        } catch (SQLException e)
        {
            return null;
        }

        return toReturn;
    }

    public boolean insert(Object o)
    {
        if (!(o instanceof Usuario))
            return false;

        Connection con = null;
        try
        {
            Usuario u = (Usuario) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("INSERT INTO USUARIOS VALUES(?, ?, ?, ?, ?, ?)");
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.isAdministrator() ? "T" : "F");
            ps.setString(6, u.isAutor() ? "T" : "F");

            ps.execute();

            con.close();
        } catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    public boolean update(Object o)
    {
        if (!(o instanceof Usuario))
            return false;

        Connection con = null;
        try
        {
            Usuario u = (Usuario) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("UPDATE USUARIOS SET USERNAME = ?, NOMBRE = ?, APELLIDOS = ?, " +
                    "PASSWORD = ?, ADMINISTRADOR = ?, AUTOR = ? WHERE LOWER (USERNAME) = LOWER (?)");
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.isAdministrator() ? "T" : "F");
            ps.setString(6, u.isAutor() ? "T" : "F");
            ps.setString(7, u.getUsername());

            ps.execute();

            con.close();
        } catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    public boolean delete(Object o)
    {
        if (!(o instanceof Usuario))
            return false;

        Connection con = null;
        try
        {
            Usuario u = (Usuario) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT A.* FROM ARTICULOS A, USUARIOS U WHERE LOWER(A.USERNAME) = LOWER(?)");
            ps.setString(1, u.getUsername());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                ArticuloServices.getInstance().delete(new Articulo(rs.getLong("id"),null,null,null,null,null,null));
            }

            ps = con.prepareStatement("DELETE FROM USUARIOS WHERE LOWER (USERNAME) = LOWER (?)");
            ps.setString(1, u.getUsername());
            ps.execute();

            con.close();
        } catch (SQLException e)
        {
            return false;
        }

        return true;
    }
}
