package webservices;

import domain.Estudiante;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

import static main.Server.estudiantes;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
@WebService()
public class EstudianteServices
{
    @WebMethod
    public void insert(int matricula, String nombre, String carrera)
    {
        estudiantes.add(new Estudiante(matricula, nombre, carrera));
    }

    @WebMethod
    public void update(int matricula, String nombre, String carrera)
    {
        for(int i = 0; i < estudiantes.size(); i++)
        {
            if(estudiantes.get(i).getMatricula() == matricula)
            {
                estudiantes.get(i).setCarrera(carrera);
                estudiantes.get(i).setNombre(nombre);
            }
        }
    }

    @WebMethod
    public void delete(int matricula)
    {
        for(int i = 0; i < estudiantes.size(); i++)
        {
            if (estudiantes.get(i).getMatricula() == matricula)
            {
                estudiantes.remove(i);
                return;
            }
        }
    }

    @WebMethod
    public ArrayList<Estudiante> select() { return estudiantes; }

    @WebMethod
    public Estudiante selectByID(int matricula)
    {
        for(int i = 0; i < estudiantes.size(); i++)
            if(estudiantes.get(i).getMatricula() == matricula)
                return estudiantes.get(i);

        return null;
    }
}
