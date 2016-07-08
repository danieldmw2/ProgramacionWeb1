package main;

import domain.Estudiante;
import webservices.AsignaturaServices;
import webservices.EstudianteServices;

import javax.xml.ws.Endpoint;
import java.util.ArrayList;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
public class Server
{
    public static ArrayList<Estudiante> estudiantes = new ArrayList<>();

    public static void main(String[] argv) throws Exception
    {
        Endpoint.publish("http://localhost:9000/Estudiantes", new EstudianteServices());
        Endpoint.publish("http://localhost:9000/Asignaturas", new AsignaturaServices());
    }
}
