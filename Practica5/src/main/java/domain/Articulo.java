package domain;

import services.ArticuloServices;
import services.ComentarioServices;
import services.UsuarioServices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.persistence.*;
import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
@Entity
public class Articulo implements Serializable
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false) private String titulo;
    @Column(nullable = false, columnDefinition = "VARCHAR(MAX)") private String cuerpo;
    @OneToOne private Usuario autor;
    @Column(nullable = false) private Date fecha;
    @Transient private List<Comentario> listaComentarios;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}) private List<Etiqueta> listaEtiquetas;
    @Column private int likes;
    @Column private int dislikes;
    @Transient Float ratio;

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

    public Float getRatio()
    {
        return ratio;
    }

    public void setRatio(Float ratio)
    {
        this.ratio = ratio;
    }

    public Integer getDislikes()
    {
        return dislikes;
    }

    public void setDislikes(Integer dislikes)
    {
        this.dislikes = dislikes;
    }

    public Integer getLikes()
    {
        return likes;
    }

    public void setLikes(Integer likes)
    {
        this.likes = likes;
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
        listaComentarios = new ArrayList<>();
        for (Comentario c : ComentarioServices.getInstance().select())
            if(c.getArticulo().getId() == this.id)
                listaComentarios.add(c);

        return listaComentarios;
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
