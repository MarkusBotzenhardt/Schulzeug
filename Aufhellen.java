import java.awt.*;

public class Aufhellen extends Filter
{
    public Aufhellen()
    {
        filtername = "Aufhellen";
    }

    @Override
    public void anwenden(Farbbild bild)
    {
        int hoehe = bild.getHeight();
        int breite = bild.getWidth();

        for(int y = 0; y <  hoehe; y++) {
            for(int x = 0; x < breite; x++) {
                Color farbe = bild.gibPunktfarbe(x, y);
                farbe = farbe.brighter();// Farbwert ändern
                bild.setzePunktfarbe(x, y, farbe);
            }
        }
    }
}
