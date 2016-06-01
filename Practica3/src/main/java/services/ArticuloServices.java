package services;

import java.sql.SQLException;

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
                "  CUERPO   VARCHAR(1000) NOT NULL,\n" +
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
}
