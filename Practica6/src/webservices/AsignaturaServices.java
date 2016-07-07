package webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

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
    public void select()
    {

    }

    @WebMethod
    public void selectByIDs(int matricula, String codigo)
    {

    }
}
