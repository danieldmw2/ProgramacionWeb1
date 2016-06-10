package domain;

import javax.persistence.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
@Entity
public class Comentario
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false) private String comentario;
    @OneToOne private Usuario autor;
    @OneToOne private Articulo articulo;

    public  Comentario()
    {

    }

    public Comentario( String comentario, Usuario autor, Articulo articulo)
    {
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
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
}
