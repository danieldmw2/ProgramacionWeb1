package webservices;

import domain.Asignatura;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
@WebService()
public class AsignaturaServices
{
    @WebMethod
    public void insert(int matricula, String codigo, String nombre)
    {

    }

    @WebMethod
    public void update(int matricula, String codigo, String nombre)
    {

    }

    @WebMethod
    public void remove(int matricula, String codigo)
    {

    }

    @WebMethod
    public ArrayList<Asignatura> select()
    {
        return new ArrayList<>();
    }

    @WebMethod
    public Asignatura selectByIDs(int matricula, String codigo)
    {
        return new Asignatura("1", "2");
    }
}
