import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Bildbetrachter ist die Hauptklasse der Bildbetrachter-Anwendung. Sie
 * erstellt die GUI der Anwendung, zeigt sie an und initialisiert alle
 * anderen Komponenten.
 * 
 * Erzeugen Sie ein Exemplar dieser Klasse, um die Anwendung zu starten.
 * 
 * @author Michael Kölling und David J Barnes 
 * @version 0.4
 */
public class Bildbetrachter
{
    // Datenfelder
    private JFrame fenster;
    private Bildflaeche bildflaeche;
    private Farbbild aktuellesBild;
    private JLabel statusLabel;
    private final String VERSION = "0.9";
    
    /**
     * Erzeuge einen Bildbetrachter und zeige seine GUI auf
     * dem Bildschirm an.
     */
    public Bildbetrachter()
    {
        fensterErzeugen();
    }

    // ---- Implementierung der Menü-Funktionen ----
    /**
     * 'Datei oeffnen'-Funktion: Öffnet einen Dateiauswahldialog zur 
     * Auswahl einer Bilddatei und zeigt das selektierte Bild an.
     */
    private void dateiOeffnen()
    {
        Farbbild bild = BilddateiManager.gibBild();
        bildflaeche.setzeBild(bild);
        aktuellesBild = bild;
        statusLabelSetzen("Neues Bild geladen.");
        fenster.pack();
    }

    /**
     * 'Beenden'-Funktion: Beendet die Anwendung.
     */
    private void beenden()
    {
        System.exit(0);
    }

    // ---- Swing-Anteil zum Erzeugen des Fensters mit allen Komponenten ----
    /**
     * Erzeuge das Swing-Fenster samt Inhalt.
     */
    private void fensterErzeugen()
    {
        fenster = new JFrame("Bildbetrachter");
        menuezeileErzeugen(fenster);

        Container contentPane = fenster.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JLabel dateinameLabel = new JLabel();
        contentPane.add(dateinameLabel,BorderLayout.NORTH);

        bildflaeche = new Bildflaeche();
        contentPane.add(bildflaeche,BorderLayout.CENTER);

        statusLabel = new JLabel("Version 1.0");
        contentPane.add(statusLabel, BorderLayout.SOUTH);

        // Aufbau abgeschlossen - Komponenten arrangieren lassen
        fenster.pack();
        fenster.setVisible(true);
    }

    /**
     * Die Menüzeile des Hauptfensters erzeugen.
     * @param fenster das Fenster, in das die Menüzeile eingefügt werden soll.
     */
    private void menuezeileErzeugen(JFrame fenster)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menuezeile = new JMenuBar();
        fenster.setJMenuBar(menuezeile);

        // Das Datei-Menü erzeugen
        JMenu dateiMenue = new JMenu("Datei");
        menuezeile.add(dateiMenue);

        JMenuItem oeffnenEintrag = new JMenuItem("Öffnen...");
        oeffnenEintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
        oeffnenEintrag.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { dateiOeffnen(); }
            });
        dateiMenue.add(oeffnenEintrag);

        JMenuItem beendenEintrag = new JMenuItem("Beenden");
        beendenEintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        beendenEintrag.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { beenden(); }
            });
        dateiMenue.add(beendenEintrag);

        // Filter-Menü erzeugen
        JMenu filterMenue = new JMenu("Filter");
        menuezeile.add(filterMenue);

        JMenuItem dunklerEintrag = new JMenuItem("Dunkler");
        dunklerEintrag.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    dunkler();
                }
            });
        filterMenue.add(dunklerEintrag);

        JMenuItem hellerEintrag = new JMenuItem("Heller");
        hellerEintrag.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    heller();
                }
            });
        filterMenue.add(hellerEintrag);

        JMenuItem schwellwertEintrag = new JMenuItem("Schwellwert");
        schwellwertEintrag.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    schwellwert();
                }
            });
        filterMenue.add(schwellwertEintrag);
        
        JMenu hilfeMenue = new JMenu("Hilfe");
        menuezeile.add(hilfeMenue);
        JMenuItem infoEintrag = new JMenuItem("Info...");
        infoEintrag.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    zeigeInfo();
                }
            });
        hilfeMenue.add(infoEintrag);
    }

    private void dunkler()
    {
        if(pruefeObBildGeladen())
        {
            statusLabelSetzen("Das Bild wird abgedunkelt.");
            aktuellesBild.abdunkeln();
            fenster.repaint();
        }
    } 

    private void heller()
    {
        if(pruefeObBildGeladen())
        {
            statusLabelSetzen("Das Bild wird aufgehellt.");
            aktuellesBild.aufhellen();
            fenster.repaint();
        }
    }

    private void schwellwert()
    {
        if(pruefeObBildGeladen())
        {
            statusLabelSetzen("Das Bild wird in Graustufen umgewandelt.");
            aktuellesBild.graustufen();
            fenster.repaint();
        }
    }
    
    public void zeigeInfo()
    {
        JOptionPane.showMessageDialog(fenster, 
                                      "Bildbetrachter Version: " + VERSION,
                                      "Information:", 
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void statusLabelSetzen(String neuerText)
    {
        statusLabel.setText(neuerText);
    }
    
    public boolean pruefeObBildGeladen()
    {
        if(aktuellesBild == null)
        {
            statusLabelSetzen("Bitte zuerst ein Bild laden!");
            return false;
        }
        return true;
    }
}
