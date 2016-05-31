/**
 * Created by Daniel's Laptop on 5/29/2016.
 */

import spark.ModelAndView;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.*;

public class Main
{
    public static void main(String[] args)
    {
        staticFiles.location("/public");

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        get("/home", (req, res) -> {
            List<Object> list = new ArrayList<>();
            list.add(new Object());
            list.add(new Object());
            HashMap<String, Object> map = new HashMap<>();
            map.put("items", list);
            map.put("title", "Let's Blog a bit!");
            map.put("intro", "Computer Science's hideout");
            map.put("user", "Ariel Salce");
            map.put("date", "May 31, 2016");
            map.put("summary", "So delicious like I can eat you up every day all day and never dislike your taste you damn yum yum babe.");
            return new ModelAndView(map, "home.ftl");
        }, freeMarker);

    }
}
