package example;

import domain.Asignatura;
import domain.Estudiante;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.URL;

import javax.jws.WebService;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
@WebService()
public class HelloWorld
{
    @WebMethod
    public String sayHelloWorldFrom(String from)
    {
        String result = "Hello, world, from " + from;
        System.out.println(result);
        return result;
    }

//    @WebMethod
//    public Asignatura sayHello()
//    {
//        Asignatura e = new Asignatura("ASDAS", "asdAS");
//        System.out.println(e);
//        return e;
//    }

    @WebMethod
    public Estudiante sayHello()
    {
        Estudiante e = new Estudiante(20090707, "asdasd", "asdasdasdas");
        System.out.println(e);
        return e;
    }

    public static void main(String[] argv) throws Exception
    {
        Object implementor = new HelloWorld();
        String address = "http://localhost:9000/HelloWorld";
        Endpoint.publish(address, implementor);
    }
}
