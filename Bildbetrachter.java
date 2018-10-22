import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Bildbetrachter ist die Hauptklasse der Bildbetrachter-Anwendung. Sie
 * erstellt die GUI der Anwendung, zeigt sie an und initialisiert alle
 * anderen Komponenten.
 * 
 * Erzeugen Sie ein Exemplar dieser Klasse, um die Anwendung zu starten.
 * 
 * @author Michael K�lling und David J Barnes 
 * @version 0.4
 */
public class Bildbetrachter
{
    // Datenfelder
    private JFrame fenster;
    private Bildflaeche bildflaeche;
    private Farbbild aktuellesBild;
    private JLabel statusLabel;
    private ArrayList<Filter> filterListe;
    private final String VERSION = "0.9";
    
    /**
     * Erzeuge einen Bildbetrachter und zeige seine GUI auf
     * dem Bildschirm an.
     */
    public Bildbetrachter()
    {
        filterListeErstellen();
        fensterErzeugen();
    }
    
    private void filterListeErstellen()
    {
        filterListe = new ArrayList<>();
        filterListe.add(new Aufhellen());
        filterListe.add(new Abdunkeln());
        filterListe.add(new Graustufen());
        filterListe.add(new HorizontalSpiegeln());
    }

    // ---- Implementierung der Men�-Funktionen ----
    /**
     * 'Datei oeffnen'-Funktion: �ffnet einen Dateiauswahldialog zur 
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
     * Die Men�zeile des Hauptfensters erzeugen.
     * @param fenster das Fenster, in das die Men�zeile eingef�gt werden soll.
     */
    private void menuezeileErzeugen(JFrame fenster)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menuezeile = new JMenuBar();
        fenster.setJMenuBar(menuezeile);

        // Das Datei-Men� erzeugen
        JMenu dateiMenue = new JMenu("Datei");
        menuezeile.add(dateiMenue);

        JMenuItem oeffnenEintrag = new JMenuItem("�ffnen...");
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

        // Filter-Men� erzeugen
        JMenu filterMenue = new JMenu("Filter");
        menuezeile.add(filterMenue);

        for(Filter filter: filterListe)
        {
            filterMenue.add(filterEintr�geErzeugen(filter));
        } 
        
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
    
    private JMenuItem filterEintr�geErzeugen(Filter filter)
    {
        JMenuItem eintrag = new JMenuItem(filter.gibFiltername());
        eintrag.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    filterAnwenden(filter);
                }
            });
        return eintrag;
    }
    
    private void filterAnwenden(Filter filter)
    {
        if(aktuellesBild != null)
        {
            filter.anwenden(aktuellesBild);
            statusLabelSetzen("Aktion ausgef�hrt: " + filter.gibFiltername());
            fenster.repaint();
        }
        else
        {
            statusLabelSetzen("Bitte zuerst ein Bild laden!");
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
    
    // public boolean pruefeObBildGeladen()
    // {
        // if(aktuellesBild == null)
        // {
            // statusLabelSetzen("Bitte zuerst ein Bild laden!");
            // return false;
        // }
        // return true;
    // }
}
