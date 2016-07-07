package main;

import example.HelloWorld;
import webservices.AsignaturaServices;
import webservices.EstudianteServices;

import javax.xml.ws.Endpoint;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
public class Server
{
    public static void main(String[] argv) throws Exception
    {
        Endpoint.publish("http://localhost:9000/Estudiantes", new EstudianteServices());
        Endpoint.publish("http://localhost:9000/Asignaturas", new AsignaturaServices());
    }
}
