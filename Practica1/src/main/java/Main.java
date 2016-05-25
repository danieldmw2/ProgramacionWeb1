/**
 * Created by Daniel's Laptop on 5/24/2016.
 */

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        code();
    }

    public static void code()
    {
        System.out.print("Escriba un URL: ");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine().trim();
        System.out.println();

        Document doc;
        try
        {
            doc = Jsoup.connect(url).get();
        }
        catch (Exception e)
        {
            System.out.println("El URL no se pudo abrir correctamente");
            code();
            return;
        }

        Elements forms =  doc.select("form");

        System.out.println("Cantidad de Lineas: " + doc.html().split("\\n").length);
        System.out.println("Cantidad de Parrafos: " + doc.select("p").size());
        System.out.println("Cantidad de Imagenes: " + doc.select("img").size());
        System.out.println("Cantidad de Formularios: " + forms.size());
        System.out.println();

        for (int i = 0; i < forms.size(); i++)
        {
            Elements inputs = forms.get(i).select("input");
            System.out.println("Formulario #" + (i + 1));
            for (int j = 0; j < inputs.size(); j++)
                System.out.println(
                        String.format("\tInput #%s: El tipo es %s %s",
                        (j + 1), inputs.get(j).attr("type"),
                        inputs.get(j).attr("submit").equalsIgnoreCase("post") ? "y es enviado por POST" : "")
                );
            System.out.println();
        }
    }
}
