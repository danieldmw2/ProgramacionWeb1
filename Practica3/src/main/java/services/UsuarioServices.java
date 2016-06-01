package services;

import java.sql.SQLException;

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
        if(instance == null)
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
        }
        catch(SQLException e)
        {
            return false;
        }

        return true;
    }
}
