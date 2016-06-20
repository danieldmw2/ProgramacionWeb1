package main;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        staticFiles.location("/public");

        // Add this line to your project to enable the debug screen
        enableDebugScreen();

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        get("/", (request, response) -> {
            return "Hola Mundo";
        });
    }
}
