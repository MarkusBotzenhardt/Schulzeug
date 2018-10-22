import java.awt.*;

public class Graustufen extends Filter
{

    public Graustufen()
    {
        filtername = "Graustufen";
    }

    @Override
    public void anwenden(Farbbild bild)
    {
        int hoehe = bild.getHeight();
        int breite = bild.getWidth();

        for(int y = 0; y <  hoehe; y++) {
            for(int x = 0; x < breite; x++) {
                Color farbe = bild.gibPunktfarbe(x, y);
                int farbwert = farbe.getRed() + farbe.getGreen() + farbe.getBlue();

                if(farbwert > 0 && farbwert <= 255)
                {
                    bild.setzePunktfarbe(x, y, Color.DARK_GRAY);
                }
                if(farbwert > 255 && farbwert <= 510)
                {
                    bild.setzePunktfarbe(x, y, Color.GRAY);
                }
                if(farbwert > 510 && farbwert < 765)
                {
                    bild.setzePunktfarbe(x, y, Color.LIGHT_GRAY);
                }
            }
        }
    }
}