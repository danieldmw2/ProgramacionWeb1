package domain;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@Entity
public class Image implements Serializable
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false) private String filename;

    @Basic(fetch = FetchType.EAGER)
    @Lob @Column(nullable=false, columnDefinition="BLOB") private byte[] image;
    @OneToOne private Album album;
    private Long views;

    @Transient
    private String base;

    public Image()
    {
    }

    public Image(String filename, Album album) throws IOException
    {
        this.filename = filename.substring(filename.lastIndexOf("\\") + 1);
        File imgPath = new File(filename);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        this.image = baos.toByteArray();
        this.base = Base64.encode(image);
        this.album = album;
        this.views = 0l;
    }

    public Long getId()
    {
        return id;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
        this.base = Base64.encode(image);
    }

    public String getBase()
    {
        base = Base64.encode(image);
        return base;
    }

    public Album getAlbum()
    {
        return album;
    }

    public void setAlbum(Album album)
    {
        this.album = album;
    }

    public Long getViews()
    {
        return views;
    }

    public void addView()
    {
        views++;
    }
}
