/**
 * Created by Daniel's Laptop on 5/25/2016.
 */
import java.sql.*;

public class Database
{
    private static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:h2:~/practica5", "user", "admin");
    }

    public static ResultSet executeQuery(String statement) throws SQLException
    {
        return getConnection().createStatement().executeQuery(statement);
    }

    public static boolean executeInsert(String... params) throws SQLException
    {
        String statement = "INSERT INTO ESTUDIANTES VALUES(";
        for(int i = 0; i < params.length; i++)
            statement += String.format("'%s'", params[i]) + ",";
        statement = (statement.substring(0, statement.lastIndexOf(",")) + ")");
        return getConnection().createStatement().execute(statement);
    }

    public static boolean executeUpdate(String[] params, String value) throws SQLException
    {
        return getConnection().createStatement().execute(
                String.format("UPDATE ESTUDIANTES SET MATRICULA = '%s', NOMBRE = '%s', APELLIDOS = '%s', TELEFONO = '%s'" +
                        " WHERE MATRICULA = '%s'",
                        params[0], params[1], params[2], params[3], value
                        ));
    }

    public static boolean executeDelete(String value) throws SQLException
    {
        return getConnection().createStatement().execute(String.format("DELETE FROM ESTUDIANTES WHERE MATRICULA = '%s'", value));
    }
}
