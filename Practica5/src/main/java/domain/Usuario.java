package domain;

import services.UsuarioServices;

import javax.persistence.*;
import java.io.Serializable;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */

@Entity
public class Usuario implements Serializable
{
    @Id private String username;
    @Column(nullable = false) private String nombre;
    @Column(nullable = false) private String apellidos;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private Boolean administrator;
    @Column(nullable = false) private Boolean autor;

    public Usuario()
    {

    }

    public Usuario(String username, String nombre, String apellidos, String password, Boolean administrator, Boolean autor)
    {
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.administrator = administrator;
        this.autor = autor;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellidos()
    {
        return apellidos;
    }

    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean isAdministrator()
    {
        return administrator;
    }

    public void setAdministrator(boolean administrator)
    {
        this.administrator = administrator;
    }

    public Boolean isAutor()
    {
        return autor;
    }

    public void setAutor(boolean autor)
    {
        this.autor = autor;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s - %s - %s - %s - %s", username, nombre, apellidos, password, administrator, autor);
    }
}
