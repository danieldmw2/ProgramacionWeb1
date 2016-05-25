/**
 * Created by Daniel's Laptop on 5/25/2016.
 */

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

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

        get("/test", (req, res) -> {
            return new ModelAndView(null, "randomTest.html");
        }, freeMarker);
    }
}
