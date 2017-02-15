package fr.silverxeon.Graphics;

import fr.silverxeon.Client.StrategieClick.Download;
import fr.silverxeon.Client.StrategieClick.Upload;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Panneau extends JPanel implements ActionListener{
    private Bouton dlFile;
    private Bouton upFile;
    private String session = "Session : ";
    private String name = "Nom : ";
    private String surname = "Prenom : ";
    private TextField textSession;
    private TextField textName;
    private TextField textSurname;


    private boolean error = false;
    private String errorTitle;
    private String errorMessage;

    public Panneau() {
        this.setLayout(null);
        dlFile = new Bouton("Télécharger le fichier", 45, 115, new Download());

        upFile = new Bouton("Rendre un fichier", 45, 170, new Upload());

        textSession = new TextField();
        textName = new TextField();
        textSurname = new TextField();

        textSession.setLocation(82, 20);
        textSession.setSize(150, 20);
        textName.setSize(150, 20);
        textSurname.setSize(150, 20);
        textName.setLocation(82, 50);
        textSurname.setLocation(82, 80);
        dlFile.addActionListener(this);
        upFile.addActionListener(this);

        this.add(dlFile);
        this.add(upFile);
        this.add(textSession);
        this.add(textName);
        this.add(textSurname);
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        g.drawString("Session : ", 26, 33);
        g.drawString("Nom : ", 45, 63);
        g.drawString("Prenom : ", 27, 93);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        session = textSession.getText();
        if(e.getSource() == dlFile)
            Download.download(session, this);
        else
            Upload.upload(session,  textName.getText(), textSurname.getText());
    }

    public void displayError(String title, String text){
        error = true;
        errorTitle = title;
        errorMessage = text;
    }

}
