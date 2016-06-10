package domain;

import services.ArticuloServices;
import services.ComentarioServices;
import services.UsuarioServices;

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
public class Articulo
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false) private String titulo;
    @Column(nullable = false, columnDefinition = "VARCHAR(MAX)") private String cuerpo;
    @OneToOne private Usuario autor;
    @Column(nullable = false) private Date fecha;
    @Transient private List<Comentario> listaComentarios;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}) private List<Etiqueta> listaEtiquetas;

    public static void main(String[] args)
    {
        enableDebugScreen();
        get("/", (request, response) -> {
            Usuario user = new Usuario("danieldmw2", "Daniel", "Perez", "culo123", true, true);
            UsuarioServices.getInstance().insert(user);
            UsuarioServices.getInstance().insert(new Usuario("arielsalcepk", "Ariel", "Salce", "culo123", true, true));

            Articulo articulo = new Articulo();
            articulo.setTitulo("Stack Overflow");
            articulo.setCuerpo("Stack Overflow me ahorra muchas noches de dolor sufriendo por tu amor");
            articulo.setAutor(user);
            articulo.setFecha(new Date());

            //ArrayList<Comentario> listC = new ArrayList<Comentario>();
//            listC.add(new Comentario("Super sexo", user, articulo));
//            listC.add(new Comentario("Te de Campana", UsuarioServices.getInstance().selectByID("arielsalcepk"), articulo));
//            listC.add(new Comentario("Si tu te fuiste con el quedate con el, que si tu me lo das yo no lo cojo", user, articulo));
//            //articulo.setListaComentarios(listC);

            ArrayList<Etiqueta> listE = new ArrayList<Etiqueta>();
            listE.add(new Etiqueta("Stack"));
            listE.add(new Etiqueta("Overflow"));
            listE.add(new Etiqueta("PUCMM"));
            listE.add(new Etiqueta("Flow"));
            listE.add(new Etiqueta("Java"));
            articulo.setListaEtiquetas(listE);

            ArticuloServices.getInstance().insert(articulo);
            return "Done Deal";
        });
    }

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
