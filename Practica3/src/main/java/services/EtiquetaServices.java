package services;

import domain.Etiqueta;

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
        return null;
    }

    protected ArrayList<Etiqueta> select(long articuloID)
    {
        return null;
    }

    protected Object selectByID(Object o)
    {
        return null;
    }

    protected boolean insert(Object o)
    {
        return true;
    }

    protected boolean update(Object o)
    {
        return true;
    }

    protected boolean delete(Object o)
    {
        return true;
    }
}
