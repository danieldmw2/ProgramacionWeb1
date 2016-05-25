/**
 * Created by Daniel's Laptop on 5/25/2016.
 */

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.ResultSet;
import java.util.HashMap;

import static spark.Spark.*;

public class Main
{
    public static void main(String[] args)
    {
        staticFiles.location("/public");

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/html");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        get("/home", (req, res) -> {

            String table = "<div class=\"uk-overflow-container\"><table class=\"uk-table uk-table-hover \">" +
                    "    <caption>Lista de Estudiantes</caption>" +
                    "    <thead>" +
                    "        <tr>" +
                    "            <th>Matricula</th>" +
                    "            <th>Nombre</th>" +
                    "            <th>Apellidos</th>" +
                    "            <th>Telefono</th>" +
                    "        </tr>" +
                    "    </thead>" +
                    "    <tbody>";

            ResultSet rs = Database.executeQuery("SELECT * FROM ESTUDIANTES");
            while (rs.next())
                table += String.format("<tr onclick=\"document.location='\\visualize?id=%s';\"><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        rs.getString("matricula"), rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"));


            HashMap<String, String> map = new HashMap<String, String>();
            map.put("table", table + "</tbody></table></div>");

            return new ModelAndView(map, "home.ftl");
        }, freeMarker);

        get("/insert", (req, res) -> {
            return new ModelAndView(null, "insert.ftl");
        }, freeMarker);

        get("/update", (req, res) -> {
            return new ModelAndView(null, "update.ftl");
        }, freeMarker);

        get("/visualize", (req, res) -> {
            String matricula = req.queryParams("id");
            return new ModelAndView(null, "visualize.ftl");
        }, freeMarker);

        post("/insertDB", (req, res) -> {
            boolean bool = Database.executeInsert(req.queryParams("matricula"), req.queryParams("nombre"), req.queryParams("apellidos"), req.queryParams("telefonos"));

            if (bool)
                res.redirect("/home");
            else
                res.status(500);
            return null;
        });
    }
}
