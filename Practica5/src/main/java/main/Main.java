package main;

/**
 * Created by Daniel's Laptop on 5/29/2016.
 */

import domain.Usuario;
import services.*;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;
import org.eclipse.jetty.websocket.api.Session;
import websockets.ServidorMensajeWebSocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import static j2html.TagCreator.*;

public class Main
{
    public static Usuario loggedInUser = null;
    public static String login = "Iniciar Sesión";
    public static List<Session> usuariosConectados = new ArrayList<>();

    public static void main(String[] args)
    {
        staticFiles.location("/public");
        enableDebugScreen();

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        webSocket("/mensajeServidor", ServidorMensajeWebSocketHandler.class);
        init();

        get("/",(request, response) ->{
            return "Ejemplo de SparkJava con WebSocket";
        });

        /**
         * http://localhost:4567/polling
         */
        get("/polling",(request, response) ->{
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return ""+format.format(new Date());
        });

        /**
         * http://localhost:4567/enviarMensaje?mensaje=Hola Mundo
         */
        get("/enviarMensaje",(request, response) ->{
            String mensaje = request.queryParams("mensaje");
            enviarMensajeAClientesConectados(mensaje);
            return "Enviando mensaje: "+mensaje;
        });


        Filtros.aplicarFiltros();
        ZonaAdmin.create(freeMarker);
        GetURLs.create(freeMarker);
        PostURLs.create(freeMarker);

        if(UsuarioServices.getInstance().selectByID("USER") == null)
            UsuarioServices.getInstance().insert(new Usuario("USER", "USER", "USER", "ADMIN", true, true));
    }

    public static void enviarMensajeAClientesConectados(String mensaje){
        for(Session sesionConectada : usuariosConectados){
            try {
                sesionConectada.getRemote().sendString(p(mensaje).withClass("rojo").render());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
