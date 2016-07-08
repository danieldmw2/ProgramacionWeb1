package domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel's Laptop on 7/7/2016.
 */
public class Estudiante
{
    private int matricula;
    private String nombre;
    private String carrera;
    private List<Asignatura> asignaturas;

    public Estudiante(int matricula, String nombre, String carrera)
    {
        this.matricula = matricula;
        this.nombre = nombre;
        this.carrera = carrera;
        this.asignaturas = new ArrayList<>();
    }

    public int getMatricula()
    {
        return matricula;
    }

    public void setMatricula(int matricula)
    {
        this.matricula = matricula;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getCarrera()
    {
        return carrera;
    }

    public void setCarrera(String carrera)
    {
        this.carrera = carrera;
    }

    public List<Asignatura> getAsignaturas()
    {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas)
    {
        this.asignaturas = asignaturas;
    }

    public void addAsignatura(Asignatura asignatura)
    {
        if(asignaturas == null)
            asignaturas = new ArrayList<>();

        asignaturas.add(asignatura);
    }

    public void clearAsignaturas()
    {
        asignaturas = null;
    }
}
