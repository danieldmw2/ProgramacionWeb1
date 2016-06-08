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
public class ArticuloServices extends DatabaseServices
{
    private ArticuloServices()
    {
        super();
    }

    private static ArticuloServices instance = null;

    public static ArticuloServices getInstance()
    {
        if(instance == null)
            instance = new ArticuloServices();

        return instance;
    }

    public boolean createTable()
    {
        UsuarioServices.getInstance().createTable();

        String statement = "CREATE TABLE ARTICULOS\n" +
                "(\n" +
                "  ID       INTEGER,\n" +
                "  TITULO   VARCHAR(200)  NOT NULL,\n" +
                "  CUERPO   VARCHAR(MAX) NOT NULL,\n" +
                "  USERNAME VARCHAR(50)   NOT NULL,\n" +
                "  FECHA    DATETIME      NOT NULL,\n" +
                "  PRIMARY KEY (ID),\n" +
                "  FOREIGN KEY (USERNAME) REFERENCES USUARIOS (USERNAME)\n" +
                ");";

        try
        {
            getConnection().createStatement().execute(statement);
            EtiquetaServices.getInstance().createTable();
            ComentarioServices.getInstance().createTable();
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

            PreparedStatement ps = con.prepareStatement("SELECT * FROM ARTICULOS");

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Articulo articulo =
                        new Articulo(
                                rs.getLong("id"),
                                rs.getString("titulo"),
                                rs.getString("cuerpo"),
                                (Usuario)UsuarioServices.getInstance().selectByID(rs.getString("username")),
                                rs.getDate("fecha"),
                                null,
                                EtiquetaServices.getInstance().select(rs.getLong("id"))
                        );
                articulo.setListaComentarios(ComentarioServices.getInstance().select(articulo));
                toReturn.add(articulo);
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
        Articulo toReturn = null;
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM ARTICULOS WHERE ID = ?");
            ps.setInt(1, (Integer) o);

            ResultSet rs = ps.executeQuery();
            rs.next();

            toReturn = new Articulo(
                    rs.getLong("id"),
                    rs.getString("titulo"),
                    rs.getString("cuerpo"),
                    (Usuario)UsuarioServices.getInstance().selectByID(rs.getString("username")),
                    rs.getDate("fecha"),
                    null,
                    EtiquetaServices.getInstance().select(rs.getLong("id"))
            );
            toReturn.setListaComentarios(ComentarioServices.getInstance().select(toReturn));

            con.close();
        }
        catch (SQLException e)
        {
            return null;
        }

        return toReturn;
    }

    public boolean insert(Object o)
    {
        if(!(o instanceof Articulo))
            return false;

        Connection con = null;
        try
        {
            Articulo a = (Articulo) o;
            a.setId(getNewID());
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("INSERT INTO ARTICULOS VALUES(?, ?, ?, ?, ?)");
            ps.setLong(1, a.getId());
            ps.setString(2, a.getTitulo());
            ps.setString(3, a.getCuerpo());
            ps.setString(4, a.getAutor().getUsername());
            ps.setDate(5, new java.sql.Date(a.getFecha().getTime()));

            ps.execute();

            for(int i = 0; i < a.getListaEtiquetas().size(); i++)
                EtiquetaServices.getInstance().insertTag(a.getListaEtiquetas().get(i), a);

            for(int i = 0; i < a.getListaComentarios().size(); i++)
                ComentarioServices.getInstance().insertComment(a.getListaComentarios().get(i), a);

            con.close();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    public boolean update(Object o)
    {
        if(!(o instanceof Articulo))
            return false;

        Connection con = null;
        try
        {
            Articulo a = (Articulo) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("DELETE FROM ARTICULO_ETIQUETA WHERE ID_ARTICULO = ?");
            ps.setLong(1, a.getId());
            ps.execute();

            ps = con.prepareStatement("UPDATE ARTICULOS SET ID = ?, TITULO = ?, CUERPO = ?, " +
                    "USERNAME = ?, FECHA = ? WHERE ID = ?");
            ps.setLong(1, a.getId());
            ps.setString(2, a.getTitulo());
            ps.setString(3, a.getCuerpo());
            ps.setString(4, a.getAutor().getUsername());
            ps.setDate(5, new java.sql.Date(a.getFecha().getTime()));
            ps.setLong(6, a.getId());

            for(int i = 0; i < a.getListaEtiquetas().size(); i++)
                EtiquetaServices.getInstance().insertTag(a.getListaEtiquetas().get(i), a);

            ps.execute();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    public boolean delete(Object o)
    {
        if(!(o instanceof Articulo))
            return false;

        Connection con = null;
        try
        {
            Articulo a = (Articulo) o;
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("DELETE FROM ARTICULO_ETIQUETA WHERE ID_ARTICULO = ?");
            ps.setLong(1, a.getId());

            ps.execute();

            ps = con.prepareStatement("DELETE FROM COMENTARIOS WHERE ID_ARTICULO = ?");
            ps.setLong(1, a.getId());

            ps.execute();

            ps = con.prepareStatement("DELETE FROM ARTICULO_COMENTARIO WHERE ID_ARTICULO = ?");
            ps.setLong(1, a.getId());

            ps.execute();

            ps = con.prepareStatement("DELETE FROM ARTICULOS WHERE ID = ?");
            ps.setLong(1, a.getId());

            ps.execute();

            con.close();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    private long getNewID()
    {
        long toReturn;
        Connection con = null;
        try
        {
            con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT ID_ARTICULO FROM IDENTIFICADORES");
            ResultSet rs = ps.executeQuery();
            rs.next();

            toReturn = rs.getLong("ID_ARTICULO");
            ps = con.prepareStatement("UPDATE IDENTIFICADORES SET ID_ARTICULO = (ID_ARTICULO + 1)");
            ps.execute();

            con.close();
        }
        catch (Exception e)
        {
            return 0;
        }

        return toReturn;
    }
}
