package main;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import domain.*;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;
import services.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        staticFiles.location("/public");
        enableDebugScreen();

        if(UsuarioServices.getInstance().selectByID("user") == null)
            UsuarioServices.getInstance().insert(new Usuario("user", "user@admin.com", "admin", true));

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        get("/*", (request, response) -> {
            Usuario guevo = new Usuario("danieldmw2", "daniel.dmw@gmail.com", "palomo123", false);
            Usuario guevoParao = new Usuario("arielsalcepk", "arielsalcepk@gmail.com", "palomo123", false);

            UsuarioServices.getInstance().insert(guevo);
            UsuarioServices.getInstance().insert(guevoParao);

            ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
            etiquetas.add(new Etiqueta("Test"));
            etiquetas.add(new Etiqueta("Test2"));
            etiquetas.add(new Etiqueta("Test3"));

            Album album = new Album();
            album.setDate(new Date());
            album.setUsuario(guevo);
            album.setListaEtiquetas(etiquetas);
            album.setInteraction(new ArrayList<>());

            ArrayList<Image> images = new ArrayList<Image>();
            String dir = "C:\\Users\\Daniel's Laptop\\Documents\\Vuze Downloads\\!JDownloader\\Imgur - - - n2PUO";
            File folder = new File(dir);
            File[] listOfFiles = folder.listFiles();

            if(listOfFiles != null)
                for (File f : listOfFiles)
                    if (f.isFile())
                        images.add(new Image(dir + "\\" + f.getName()));

            album.setImages(images);
            AlbumServices.getInstance().insert(album);

            Comentario calvo = new Comentario("Yo mori xD", guevo, album);
            Comentario negro = new Comentario("Ataque al corazon instantaneo", guevoParao, album);

            ComentarioServices.getInstance().insert(negro);
            ComentarioServices.getInstance().insert(calvo);

            negro.addLike(guevoParao);
            negro.addLike(guevo);

            Album album2 = new Album();
            album2.setDate(new Date());
            album2.setUsuario(guevo);

            etiquetas = new ArrayList<Etiqueta>();
            etiquetas.add(new Etiqueta("Test"));
            etiquetas.add(new Etiqueta("Test2"));
            etiquetas.add(new Etiqueta("Test3"));
            etiquetas.add(new Etiqueta("Test4"));
            
            album2.setListaEtiquetas(etiquetas);
            album2.setInteraction(new ArrayList<>());

            images = new ArrayList<Image>();
            dir = "C:\\Users\\Daniel's Laptop\\Documents\\Square Enix\\Flow";
            folder = new File(dir);
            listOfFiles = folder.listFiles();

            if(listOfFiles != null)
                for (File f : listOfFiles)
                    if (f.isFile())
                        images.add(new Image(dir + "\\" + f.getName()));

            album2.setImages(images);
            AlbumServices.getInstance().insert(album2);

            Comentario calvo2 = new Comentario("Chupala prieto del culo", guevoParao, album2);
            ComentarioServices.getInstance().insert(calvo2);
            calvo2.addLike(guevo);
            calvo2.addLike(guevo);

            album.addLike(guevoParao);
            album.addDislike(guevoParao);
            album2.addDislike(guevoParao);

            return "Guevo Parao";
        });

        get("/imageTest", (request, response) -> {
            Image image = new Image();
            image.setFilename("Sexo");

            String filename = "C:\\Users\\Daniel's Laptop\\Dot.jpg";

            File imgPath = new File(filename);
            BufferedImage bufferedImage = ImageIO.read(imgPath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] bytes = baos.toByteArray();

            image.setImage(bytes);

            FileOutputStream fos = new FileOutputStream("C:\\Users\\Daniel's Laptop\\test.png");
            fos.write(bytes);

            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("id", "id");
            model.put("image", image);

            return new ModelAndView(model, "test.ftl");
        }, freeMarker);
    }
}
