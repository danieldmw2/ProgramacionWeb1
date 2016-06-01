package services;

import domain.Articulo;
import domain.Comentario;
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
public class ComentarioServices extends DatabaseServices
{
    private ComentarioServices()
    {
        super();
    }

    private static ComentarioServices instance = null;

    public static ComentarioServices getInstance()
    {
        if(instance == null)
            instance = new ComentarioServices();

        return instance;
    }

    public boolean createTable()
    {
        String statement = "CREATE TABLE COMENTARIOS\n" +
                "(\n" +
                "  ID          INTEGER,\n" +
                "  COMENTARIO  VARCHAR(500) NOT NULL,\n" +
                "  USERNAME    VARCHAR(50)  NOT NULL,\n" +
                "  ID_ARTICULO INTEGER      NOT NULL,\n" +
                "  PRIMARY KEY (ID),\n" +
                "  FOREIGN KEY (USERNAME) REFERENCES USUARIOS (USERNAME),\n" +
                "  FOREIGN KEY (ID_ARTICULO) REFERENCES ARTICULOS (ID)\n" +
                ");";

        try
        {
            getConnection().createStatement().execute(statement);
            statement = "CREATE TABLE ARTICULO_COMENTARIO\n" +
                    "(\n" +
                    "  ID_ARTICULO   INTEGER,\n" +
                    "  ID_COMENTARIO INTEGER,\n" +
                    "  PRIMARY KEY (ID_COMENTARIO, ID_ARTICULO),\n" +
                    "  FOREIGN KEY (ID_ARTICULO) REFERENCES ARTICULOS (ID),\n" +
                    "  FOREIGN KEY (ID_COMENTARIO) REFERENCES COMENTARIOS (ID)\n" +
                    ");";
            getConnection().createStatement().execute(statement);
        }
        catch(SQLException e)
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

            PreparedStatement ps = con.prepareStatement("SELECT * FROM COMENTARIOS");

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                toReturn.add(
                        new Comentario(
                                rs.getLong("id"),
                                rs.getString("comentario"),
                                (Usuario)(UsuarioServices.getInstance().selectByID(rs.getString("username"))),
                                (Articulo)(ArticuloServices.getInstance().selectByID(rs.getInt("ID_ARTICULO"))))
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

    public ArrayList<Comentario> select(long comentarioID)
    {
        Connection con = null;
        ArrayList<Comentario> toReturn = new ArrayList<Comentario>();
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                            "SELECT C.* " +
                            "FROM ARTICULO_COMENTARIO AC, COMENTARIOS C " +
                            "WHERE AC.ID_ARTICULO = ? AND C.ID = AE.ID_COMENTARIO");
            ps.setLong(1, comentarioID);

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                toReturn.add(
                        new Comentario(
                                rs.getLong("id"),
                                rs.getString("comentario"),
                                (Usuario)(UsuarioServices.getInstance().selectByID(rs.getString("username"))),
                                (Articulo)(ArticuloServices.getInstance().selectByID(rs.getInt("ID_ARTICULO"))))
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

    public Object selectByID(Object o)
    {
        Connection con = null;
        Comentario toReturn = null;
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM ETIQUETAS WHERE ID = ?");
            ps.setInt(1, (Integer) o);

            ResultSet rs = ps.executeQuery();
            rs.next();

            toReturn = new Comentario(
                            rs.getLong("id"),
                            rs.getString("comentario"),
                            (Usuario)(UsuarioServices.getInstance().selectByID(rs.getString("username"))),
                            (Articulo)(ArticuloServices.getInstance().selectByID(rs.getInt("ID_ARTICULO"))));

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
        if(!(o instanceof Comentario))
            return false;

        Connection con = null;
        try
        {
            Comentario c = (Comentario) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("INSERT INTO COMENTARIOS VALUES(?, ?, ?, ?)");
            ps.setLong(1, c.getId());
            ps.setString(2, c.getComentario());
            ps.setString(3, c.getAutor().getUsername());
            ps.setLong(4, c.getArticulo().getId());

            ps.execute();

            con.close();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    public boolean insertComment(Comentario c, Articulo a)
    {
        insert(c);
        Connection con = null;

        try
        {
            con = getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("INSERT INTO ARTICULO_COMENTARIO VALUES(?, ?)");
            ps.setLong(1, a.getId());
            ps.setLong(2, c.getId());

            ps.execute();
        }
        catch (SQLException ex)
        {
            return false;
        }

        return true;
    }

    public boolean update(Object o)
    {
        if(!(o instanceof Comentario))
            return false;

        Connection con = null;
        try
        {
            Comentario c = (Comentario) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("UPDATE COMENTARIOS SET ID = ?, COMENTARIO = ?, " +
                    "USERNAME = ?, ID_ARTICULO = ? WHERE ID = ?");

            ps.setLong(1, c.getId());
            ps.setString(2, c.getComentario());
            ps.setString(3, c.getAutor().getUsername());
            ps.setLong(4, c.getArticulo().getId());
            ps.setLong(5, c.getId());

            ps.execute();

            con.close();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    public boolean delete(Object o)
    {
        if(!(o instanceof Comentario))
            return false;

        Connection con = null;
        try
        {
            Comentario c = (Comentario) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("DELETE FROM COMENTARIOS WHERE ID = ?");
            ps.setLong(1, c.getId());

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
