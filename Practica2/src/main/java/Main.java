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
            return new ModelAndView(null, "home.ftl");
        }, freeMarker);

        get("/insert", (req, res) -> {
            return new ModelAndView(null, "insert.ftl");
        }, freeMarker);

        get("/update", (req, res) -> {
            return new ModelAndView(null, "update.ftl");
        }, freeMarker);

        post("/insertDB", (req, res) -> {
            boolean bool = Database.executeInsert(req.queryParams("matricula"), req.queryParams("nombre"), req.queryParams("apellidos"), req.queryParams("telefonos"));

            if(bool)
                res.redirect("/home");
            else
                res.status(500);
            return null;
        });
    }
}
