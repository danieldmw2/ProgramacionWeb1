/**
 * Created by Daniel's Laptop on 5/25/2016.
 */

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Database.executeInsert("20110839", "Daniel", "Perez", "8296400431");
            Database.executeInsert("20020584", "Manuel", "Perez", "8094415151");
            Database.executeInsert("20000422", "Wendy", "Perez", "6472301482");
            Database.executeInsert("2005537", "Pepeto", "Gonzales", "8096355685");

            Database.executeUpdate(new String[]{"20110839", "Daniel Amaury", "Perez Martinez", "8296400431"}, "20110839");
            Database.executeDelete("2005537");

            ResultSet rs = Database.executeQuery("SELECT * FROM ESTUDIANTES");
            while(rs.next())
                System.out.println(String.format("%s, %s, %s, %s",
                        rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono")));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

//        staticFiles.location("/public");
//
//        Configuration configuration = new Configuration();
//        configuration.setClassForTemplateLoading(Main.class, "/html");
//        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
//        freeMarker.setConfiguration(configuration);
//
//        get("/test", (req, res) -> {
//            return new ModelAndView(null, "home.ftl");
//        }, freeMarker);
    }
}
