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
                table += String.format("<tr onclick=\"document.location='\\vvisualize?id=%s';\"><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        rs.getString("matricula"), rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"));


            HashMap<String, String> map = new HashMap<String, String>();
            map.put("table", table + "</tbody></table></div>");

            return new ModelAndView(map, "home.ftl");
        }, freeMarker);

        get("/insert", (req, res) -> {
            return new ModelAndView(null, "insert.ftl");
        }, freeMarker);

        get("/update", (req, res) -> {

            String matricula = req.queryParams("matricula");

            ResultSet rs = Database.executeQuery(String.format("SELECT * FROM ESTUDIANTES WHERE MATRICULA = '%s'", matricula));
            rs.next();

            String form = String.format(
                    "<form class=\"uk-form\" action=\"\\updateDB\" method=\"post\"><fieldset data-uk-margin>" +
                    "Matricula: <input type=\"text\" name=\"matricula\" value=\"%s\">" +
                    "Nombre: <input type=\"text\" name=\"nombre\" value=\"%s\">" +
                    "Apellidos: <input type=\"text\" name=\"apellidos\" value=\"%s\">" +
                    "Telefono: <input type=\"text\" name=\"telefono\" value=\"%s\">" +
                    "<input type=\"hidden\" name=\"hiddenBox\" value=\"%s\">" +
                    "<button class=\"uk-button\">Update</button>" +
                    "</fieldset></form>", rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"), rs.getString("matricula"));

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("form", form);

            return new ModelAndView(map, "update.ftl");
        }, freeMarker);

        get("/visualize", (req, res) -> {
            String matricula = req.queryParams("id");

            ResultSet rs = Database.executeQuery(String.format("SELECT * FROM ESTUDIANTES WHERE MATRICULA = '%s'", matricula));
            rs.next();

            String form = String.format("<form class=\"uk-form\" action=\"\\decide\" method=\"post\"><fieldset data-uk-margin> Matricula: <input type=\"text\" name=\"matricula\" value=\"%s\" readonly>" +
                    "Nombre: <input type=\"text\" name=\"nombre\" value=\"%s\" readonly>" +
                    "Apellidos: <input type=\"text\" name=\"apellidos\" value=\"%s\" readonly>" +
                    "Telefono: <input type=\"text\" name=\"telefono\" value=\"%s\" readonly>" +
                    "<input type=\"submit\" name=\"action\" value=\"Update\" />\n" +
                    "<input type=\"submit\" name=\"action\" value=\"Delete\" />" +
                    "</fieldset></form>", rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"));

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("form", form);

            return new ModelAndView(map, "visualize.ftl");
        }, freeMarker);

        post("/insertDB", (req, res) -> {
            Database.executeInsert(req.queryParams("matricula"), req.queryParams("nombre"), req.queryParams("apellidos"), req.queryParams("telefono"));
            res.redirect("/home");
            return null;
        });

        post("/updateDB", (req, res) -> {
            Database.executeUpdate(new String[]{ req.queryParams("matricula"), req.queryParams("nombre"), req.queryParams("apellidos"), req.queryParams("telefono")}, req.queryParams("hiddenBox"));
            res.redirect("/home");
            return null;
        });

        post("/decide", (req, res) -> {
            String decide = req.queryParams("action");
            if (decide.equalsIgnoreCase("Update"))
                res.redirect("/update?matricula=" + req.queryParams("matricula"));
            else
            {
                Database.executeDelete(req.queryParams("matricula"));
                res.redirect("/home");
            }
            return null;
        });
    }
}
