package domain;

import services.AlbumServices;
import services.ComentarioServices;
import services.EtiquetaServices;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */
@Entity
public class Album implements Serializable
{
    @Id @GeneratedValue private Long id;
    @OneToOne private Usuario usuario;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}) private List<Image> images;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}) private List<Usuario> interaction;
    @Column(nullable = false) private Integer likes;
    @Column(nullable = false) private Integer dislikes;
    @Column(nullable = false) private Date date;
    @Column(nullable = false, columnDefinition = "VARCHAR(1000)") private String descripcion;
    @Transient private List<Comentario> listaComentarios;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}) private List<Etiqueta> listaEtiquetas;
    private Long views;

    public Album()
    {
        likes = 0;
        dislikes = 0;
        views = 0l;
        date = new Date();
        interaction = new ArrayList<>();
    }

    public Album(Usuario usuario, List<Image> images, Integer likes, Integer dislikes, Date date, List<Comentario> listaComentarios)
    {
        this.usuario = usuario;
        this.images = images;
        this.interaction = new ArrayList<>();
        this.likes = likes;
        this.dislikes = dislikes;
        this.date = date;
        this.listaComentarios = listaComentarios;
        this.views = 0l;
    }

    public Long getId()
    {
        return id;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public List<Image> getImages()
    {
        return images;
    }

    public void setImages(List<Image> images)
    {
        this.images = images;
    }

    public List<Usuario> getInteraction()
    {
        return interaction;
    }

    public void setInteraction(List<Usuario> interaction)
    {
        this.interaction = interaction;
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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<Comentario> getListaComentarios()
    {
        listaComentarios = new ArrayList<>();
        for (Comentario c : ComentarioServices.getInstance().select())
            if(c.getAlbum().getId().equals(this.id))
                listaComentarios.add(c);

        return listaComentarios;
    }

    public List<Etiqueta> getListaEtiquetas()
    {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<Etiqueta> etiquetas)
    {
        this.listaEtiquetas = etiquetas;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public void addLike(Usuario usuario)
    {
        if(this.interaction.contains(usuario))
            return;

        this.likes++;
        this.interaction.add(usuario);
        AlbumServices.getInstance().update(this);
    }

    public Long getViews()
    {
        return views;
    }

    public void addDislike(Usuario usuario)
    {
        if (this.interaction.contains(usuario))
            return;

        this.dislikes++;
        this.interaction.add(usuario);
        AlbumServices.getInstance().update(this);
    }

    public void addView()
    {
        views++;
    }
}
