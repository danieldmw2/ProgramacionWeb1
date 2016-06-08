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

            PreparedStatement ps = con.prepareStatement("DELETE FROM COMENTARIOS");
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

    public static void startUp()
    {
        Connection con = null;
        try
        {
            con = DriverManager.getConnection("jdbc:h2:~/practica4", "user", "admin");

            PreparedStatement ps = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS USUARIOS\n" +
                    "(\n" +
                    "  USERNAME      VARCHAR(50),\n" +
                    "  NOMBRE        VARCHAR(100) NOT NULL,\n" +
                    "  APELLIDOS     VARCHAR(100) NOT NULL,\n" +
                    "  PASSWORD      VARCHAR(50)  NOT NULL,\n" +
                    "  ADMINISTRADOR BOOLEAN      NOT NULL,\n" +
                    "  AUTOR         BOOLEAN      NOT NULL,\n" +
                    "  PRIMARY KEY (USERNAME)\n" +
                    ")");
            ps.execute();

            ps = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS ARTICULOS\n" +
                    "(\n" +
                    "  ID       BIGINT,\n" +
                    "  TITULO   VARCHAR(200)  NOT NULL,\n" +
                    "  CUERPO   VARCHAR(MAX)  NOT NULL,\n" +
                    "  USERNAME VARCHAR(50)   NOT NULL,\n" +
                    "  FECHA    DATETIME      NOT NULL,\n" +
                    "  PRIMARY KEY (ID),\n" +
                    "  FOREIGN KEY (USERNAME) REFERENCES USUARIOS (USERNAME)\n" +
                    ")");
            ps.execute();

            ps = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS ETIQUETAS\n" +
                    "(\n" +
                    "  ID       BIGINT,\n" +
                    "  ETIQUETA VARCHAR(50) NOT NULL,\n" +
                    "  PRIMARY KEY (ID)\n" +
                    ")");
            ps.execute();

            ps = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS ARTICULO_ETIQUETA\n" +
                    "(\n" +
                    "  ID_ARTICULO BIGINT,\n" +
                    "  ID_ETIQUETA BIGINT,\n" +
                    "  PRIMARY KEY (ID_ETIQUETA, ID_ARTICULO),\n" +
                    "  FOREIGN KEY (ID_ARTICULO) REFERENCES ARTICULOS (ID),\n" +
                    "  FOREIGN KEY (ID_ETIQUETA) REFERENCES ETIQUETAS (ID)\n" +
                    ")");
            ps.execute();

            ps = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS COMENTARIOS\n" +
                    "(\n" +
                    "  ID          BIGINT,\n" +
                    "  COMENTARIO  VARCHAR(500) NOT NULL,\n" +
                    "  USERNAME    VARCHAR(50)  NOT NULL,\n" +
                    "  ID_ARTICULO BIGINT      NOT NULL,\n" +
                    "  PRIMARY KEY (ID),\n" +
                    "  FOREIGN KEY (USERNAME) REFERENCES USUARIOS (USERNAME),\n" +
                    "  FOREIGN KEY (ID_ARTICULO) REFERENCES ARTICULOS (ID)\n" +
                    ")");
            ps.execute();

            ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS IDENTIFICADORES\n" +
                    "(\n" +
                    "  ID_ARTICULO BIGINT,\n" +
                    "  ID_COMENTARIO BIGINT,\n" +
                    "  ID_ETIQUETA BIGINT\n" +
                    ")");
            ps.execute();

            ps = con.prepareStatement("INSERT INTO USUARIOS VALUES('USER', 'USER', 'USER', 'ADMIN', TRUE, TRUE)");
            ps.execute();

            con.close();

        }
        catch (SQLException e)
        {
        }
    }

    protected abstract ArrayList<Object> select();
    protected abstract Object selectByID(Object o);
    protected abstract boolean insert(Object o);
    protected abstract boolean update(Object o);
    protected abstract boolean delete(Object o);
}
