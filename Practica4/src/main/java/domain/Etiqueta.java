package domain;

import javax.persistence.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
@Entity
public class Etiqueta
{
    @Id @GeneratedValue private Long id;
    @Column(unique = true, nullable = false) private String etiqueta;

    public Etiqueta()
    {

    }

    public Etiqueta(String etiqueta)
    {
        this.etiqueta = etiqueta;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getEtiqueta()
    {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta)
    {
        this.etiqueta = etiqueta;
    }
}

