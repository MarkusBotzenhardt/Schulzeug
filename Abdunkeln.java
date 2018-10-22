import java.awt.*;

public class Abdunkeln extends Filter
{

    public Abdunkeln()
    {
        filtername = "Abdunkeln";
    }

    @Override
    public void anwenden(Farbbild bild)
    {
        int hoehe = bild.getHeight();
        int breite = bild.getWidth();

        for(int y = 0; y <  hoehe; y++) {
            for(int x = 0; x < breite; x++) {
                Color farbe = bild.gibPunktfarbe(x, y);
                farbe = farbe.darker();// Farbwert �ndern
                bild.setzePunktfarbe(x, y, farbe);
            }
        }
    }
}
