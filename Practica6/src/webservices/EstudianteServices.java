package webservices;

import domain.Estudiante;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
@WebService()
public class EstudianteServices
{
    @WebMethod
    public void insert(int matricula, String nombre, String carrera)
    {

    }

    @WebMethod
    public void update(int matricula, String nombre, String carrera)
    {

    }

    @WebMethod
    public void delete(int matricula)
    {

    }

    @WebMethod
    public ArrayList<Estudiante> select()
    {
        return new ArrayList<>();
    }

    @WebMethod
    public Estudiante selectByID(int matricula)
    {
        return new Estudiante(1, "2", "3");
    }
}
