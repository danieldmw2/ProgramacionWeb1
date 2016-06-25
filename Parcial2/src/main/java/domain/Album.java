package domain;

import services.AlbumServices;
import services.ComentarioServices;

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
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}) private List<Usuario> interaction;
    @Column(nullable = false) private Integer likes;
    @Column(nullable = false) private Integer dislikes;
    @Column(nullable = false) private Date date;
    @Transient private List<Comentario> listaComentarios;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}) private List<Etiqueta> listaEtiquetas;

    public Album()
    {
        likes = 0;
        dislikes = 0;
        date = new Date();
    }

    public Album(Usuario usuario, List<Image> images, List<Usuario> interaction, Integer likes, Integer dislikes, Date date, List<Comentario> listaComentarios)
    {
        this.usuario = usuario;
        this.images = images;
        this.interaction = interaction;
        this.likes = likes;
        this.dislikes = dislikes;
        this.date = date;
        this.listaComentarios = listaComentarios;
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

    public void setListaEtiquetas(List<Etiqueta> listaEtiquetas)
    {
        this.listaEtiquetas = listaEtiquetas;
    }


    public void addLike(Usuario usuario)
    {
        if(this.interaction.contains(usuario))
            return;

        this.likes++;
        this.interaction.add(usuario);
        AlbumServices.getInstance().update(this);
    }

    public void addDislike(Usuario usuario)
    {
        if(this.interaction.contains(usuario))
            return;

        this.dislikes++;
        this.interaction.add(usuario);
        AlbumServices.getInstance().update(this);
    }
}
