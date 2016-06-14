package services;

import domain.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class ArticuloServices extends DatabaseServices<Articulo>
{

    private static ArticuloServices instance;

    private ArticuloServices()
    {
        super(Articulo.class);
    }

    public static ArticuloServices getInstance()
    {
        if (instance == null)
        {
            instance = new ArticuloServices();
        }
        return instance;
    }
}
