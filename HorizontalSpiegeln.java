import java.awt.*;

public class HorizontalSpiegeln extends Filter
{

    public HorizontalSpiegeln()
    {
        filtername = "Horizontal spiegeln";
    }

    @Override
    public void anwenden(Farbbild bild)
    {
        int hoehe = bild.getHeight();
        int breite = bild.getWidth();
        Color[][] bildCopy = new Color[breite][hoehe];
        
        for(int y = 0; y <  hoehe; y++) {
            for(int x = 0; x < breite; x++) {
                bildCopy[x][y] = bild.gibPunktfarbe(x,y);
            }
        }
        for(int y = 0; y <  hoehe; y++) {
            for(int x = 0; x < breite; x++) {
                bild.setzePunktfarbe(bildCopy.length-x-1,y, bildCopy[x][y]);
            }
        }
    }
}