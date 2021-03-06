package webservices;

import domain.Asignatura;
import domain.Estudiante;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

import static main.Server.estudiantes;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
@WebService()
public class AsignaturaServices
{
    @WebMethod
    public void insert(int matricula, String codigo, String nombre)
    {
        for (int i = 0; i < estudiantes.size(); i++)
            if (estudiantes.get(i).getMatricula() == matricula)
                estudiantes.get(i).addAsignatura(new Asignatura(codigo, nombre));
    }

    @WebMethod
    public void update(int matricula, String codigo, String nombre)
    {
        for (int i = 0; i < estudiantes.size(); i++)
            if (estudiantes.get(i).getMatricula() == matricula)
                for (int j = 0; j < estudiantes.get(i).getAsignaturas().size(); j++)
                    if (estudiantes.get(i).getAsignaturas().get(j).getCodigo().equals(codigo))
                        estudiantes.get(i).getAsignaturas().get(j).setNombre(nombre);
    }

    @WebMethod
    public void remove(int matricula, String codigo)
    {
        for (int i = 0; i < estudiantes.size(); i++)
        {
            if (estudiantes.get(i).getMatricula() == matricula)
            {
                for (int j = 0; j < estudiantes.get(i).getAsignaturas().size(); j++)
                {
                    if (estudiantes.get(i).getAsignaturas().get(j).getCodigo().equals(codigo))
                    {
                        estudiantes.get(i).getAsignaturas().remove(j);
                        return;
                    }
                }
            }
        }
    }
}
