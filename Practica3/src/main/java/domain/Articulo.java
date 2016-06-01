package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class Articulo
{
    private long id;
    private String titulo;
    private String cuerpo;
    private Usuario autor;
    private Date fecha;
    private ArrayList<Comentario> listaComentarios;
    private ArrayList<Etiqueta> listaEtiquetas;

    public Articulo(long id, String titulo, String cuerpo, Usuario autor, Date fecha, ArrayList<Comentario> listaComentarios, ArrayList<Etiqueta> listaEtiquetas)
    {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
        this.listaComentarios = listaComentarios;
        this.listaEtiquetas = listaEtiquetas;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getCuerpo()
    {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo)
    {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutor()
    {
        return autor;
    }

    public void setAutor(Usuario autor)
    {
        this.autor = autor;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public ArrayList<Comentario> getListaComentarios()
    {
        return listaComentarios;
    }

    public void setListaComentarios(ArrayList<Comentario> listaComentarios)
    {
        this.listaComentarios = listaComentarios;
    }

    public ArrayList<Etiqueta> getListaEtiquetas()
    {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(ArrayList<Etiqueta> listaEtiquetas)
    {
        this.listaEtiquetas = listaEtiquetas;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s - %s - %s - %s - %s", id, titulo, cuerpo, fecha.toString(), listaComentarios.toString(), listaEtiquetas.toString());
    }
}
