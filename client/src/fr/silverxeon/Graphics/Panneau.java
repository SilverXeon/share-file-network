package fr.silverxeon.Graphics;

import fr.silverxeon.Graphics.StrategieClick.Download;
import fr.silverxeon.Graphics.StrategieClick.Upload;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;

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
    private int session;
    private TextField textField;


    private boolean error = false;
    private String errorTitle;
    private String errorMessage;

    public Panneau() {
        this.setLayout(null);
        dlFile = new Bouton("Télécharger le fichier", 45, 100, new Download());

        upFile = new Bouton("Rendre un fichier", 45, 170, new Upload());

        textField = new TextField();

        textField.setLocation(45, 50);
        textField.setSize(200, 50);
        dlFile.addActionListener(this);
        upFile.addActionListener(this);
        this.add(dlFile);
        this.add(upFile);
        this.add(textField);
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        Font font = new Font("Courier", Font.BOLD, 18);
        g.setFont(font);
        g.drawString("Entrer un numéro de session : ", 14, 30);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        session = Integer.parseInt(textField.getText());
        if(e.getSource() == dlFile)
            Download.download(session, this);
        else
            Upload.upload(session, this);
    }

    public void displayError(String title, String text){
        error = true;
        errorTitle = title;
        errorMessage = text;
    }

    public void checkError(){

    }
}
