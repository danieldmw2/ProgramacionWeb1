package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
@Entity
public class Comentario implements Serializable
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false) private String comentario;
    @OneToOne private Usuario autor;
    @OneToOne private Articulo articulo;
    @Column private int likes;
    @Column private int dislikes;
    @Transient Float ratio;


    public  Comentario()
    {

    }

    public Comentario( String comentario, Usuario autor, Articulo articulo)
    {
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
        this.dislikes = 0;
        this.likes = 0;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getComentario()
    {
        return comentario;
    }

    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }

    public Usuario getAutor()
    {
        return autor;
    }

    public void setAutor(Usuario autor)
    {
        this.autor = autor;
    }

    public Articulo getArticulo()
    {
        return articulo;
    }

    public void setArticulo(Articulo articulo)
    {
        this.articulo = articulo;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getLikes()
    {
        return likes;
    }

    public void setLikes(Integer likes)
    {
        this.likes = likes;
    }

    public Integer getDislikes()
    {
        return dislikes;
    }

    public void setDislikes(Integer dislikes)
    {
        this.dislikes = dislikes;
    }

    public Float getRatio()
    {
        ratio = Float.valueOf(likes/dislikes);
        return ratio;
    }
}
