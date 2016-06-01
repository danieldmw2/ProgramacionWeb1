package services;

import java.sql.SQLException;

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
}
