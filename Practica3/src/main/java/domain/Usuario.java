package domain;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class Usuario
{
    private String username;
    private String nombre;
    private String apellidos;
    private String password;
    private boolean administrator;
    private boolean autor;

    public Usuario(String username, String nombre, String apellidos, String password, boolean administrator, boolean autor)
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

    public boolean isAdministrator()
    {
        return administrator;
    }

    public void setAdministrator(boolean administrator)
    {
        this.administrator = administrator;
    }

    public boolean isAutor()
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
