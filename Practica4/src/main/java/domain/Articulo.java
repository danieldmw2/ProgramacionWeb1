package domain;

import java.util.List;
import java.util.Date;
import javax.persistence.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
@Entity
public class Articulo
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false) private String titulo;
    @Column(nullable = false) private String cuerpo;
    @OneToOne(optional = false) private Usuario autor;
    @Column(nullable = false) private Date fecha;
    @OneToMany private List<Comentario> listaComentarios;
    @OneToMany private List<Etiqueta> listaEtiquetas;

    public Articulo()
    {

    }

    public Articulo(String titulo, String cuerpo, Usuario autor, Date fecha, List<Comentario> listaComentarios, List<Etiqueta> listaEtiquetas)
    {
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

    public List<Comentario> getListaComentarios()
    {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios)
    {
        this.listaComentarios = listaComentarios;
    }

    public List<Etiqueta> getListaEtiquetas()
    {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<Etiqueta> listaEtiquetas)
    {
        this.listaEtiquetas = listaEtiquetas;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s - %s - %s - %s - %s", id, titulo, cuerpo, fecha.toString(), listaComentarios.toString(), listaEtiquetas.toString());
    }


}
