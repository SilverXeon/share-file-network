package fr.silverxeon.Graphics;

import fr.silverxeon.Client.StrategieClick.Admin.Session;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Pierre on 14/02/2017.
 */
public class PanneauAdmin extends Panneau implements ActionListener{
    private Bouton createSession;
    private Bouton recup = new Bouton("Recuperer les fichiers", 45, 310, null);
    private String maSession = null;

    public PanneauAdmin() {
        createSession = new Bouton("Creer une session", 45, 230, null);
        createSession.addActionListener(this);
        this.add(createSession);
        recup.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font f = new Font("Courrier", Font.PLAIN, 14);
        g.setFont(f);
        if(maSession != null)
            g.drawString("Votre numéro de session est : "+maSession, 30, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createSession) {
            maSession = Session.creerSession();
            this.add(recup);
        }
        else if(e.getSource() == recup){
            System.out.println("Recup");
            Session.recupZip(maSession);
        }
        else
            super.actionPerformed(e);
    }
}
