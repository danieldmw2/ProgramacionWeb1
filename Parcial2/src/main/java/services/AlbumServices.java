package services;

import domain.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class AlbumServices extends DatabaseServices<Album>
{
    private static AlbumServices instance;

    private AlbumServices()
    {
        super(Album.class);
    }

    public static AlbumServices getInstance()
    {
        if (instance == null)
        {
            instance = new AlbumServices();
        }
        return instance;
    }
}
