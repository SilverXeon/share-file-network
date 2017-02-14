package fr.silverxeon.Client.StrategieClick;

import fr.silverxeon.Graphics.Panneau;
import fr.silverxeon.client.ClientInter;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;

import javax.activation.DataHandler;
import javax.swing.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Download implements StrategieClick {
    public static JFXPanel pan = null;
    @Override
    public void reagir() {
    }

    public static void download(String session, Panneau p){
        if(session.length() == 0){
            if(pan == null) {                 pan = new JFXPanel();             }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Veuillez verifier le numéro de session.");
                    alert.setContentText("Veuillez contacter une personne compétente en cas de récidive.");
                    alert.showAndWait();
                }
            });
            return;
        }
        ClientInter inst = null;
        try{
            URL url = new URL("http://localhost:9999/share/client?wsdl");
            QName qname = new QName("http://client.silverxeon.fr/", "ClientImplService");


            Service service = Service.create(url, qname);
            inst = service.getPort(ClientInter.class);
        }catch(MalformedURLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        DataHandler d = null;
        try{
            d = inst.download(session);
        }catch(Exception e){
            if(pan == null) {                 pan = new JFXPanel();             }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Session inexistante : "+session);
                    alert.setContentText("Veuillez contacter une personne compétente en cas de récidive");
                    alert.showAndWait();
                }
            });
            return;
        }

        try{
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("fichier."+inst.getExtension(session)));
            int returnVal = fc.showSaveDialog(null);
            InputStream i = d.getInputStream();

            //OutputStream o = new FileOutputStream(new File(fc.getSelectedFile().getAbsolutePath()));
            Files.copy(i, Paths.get(fc.getSelectedFile().getAbsolutePath()));
        }catch(IOException e){
            if(pan == null) {
                pan = new JFXPanel();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur de fichier");
                    alert.setContentText("Veuillez contacter une personne compétente en cas de récidive");
                    alert.showAndWait();
                }
            });
            return;
        }
    }
}
