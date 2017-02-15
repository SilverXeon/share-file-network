package fr.silverxeon.Client.StrategieClick.Admin;

import fr.silverxeon.admin.AdminInter;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.swing.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.silverxeon.Client.Main.IP;
import static fr.silverxeon.Client.Main.PORT;

/**
 * Created by Pierre on 14/02/2017.
 */
public class Session {
    public static String creerSession(){
        AdminInter inst = null;
        try{
            URL url = new URL("http://"+IP+":"+PORT+"/share/admin?wsdl");
            QName qname = new QName("http://admin.silverxeon.fr/", "AdminImplService");
            Service service = Service.create(url, qname);
            inst = service.getPort(AdminInter.class);
        }catch(MalformedURLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        JFileChooser jf = new JFileChooser();
        int returnVal = jf.showOpenDialog(null);
        FileDataSource f = new FileDataSource(jf.getSelectedFile());
        DataHandler d = new DataHandler(f);
        return inst.upload(d, jf.getSelectedFile().getName().substring(jf.getSelectedFile().getName().lastIndexOf('.')+1));
    }

    public static void recupZip(String session){
        AdminInter inst = null;
        try{
            URL url = new URL("http://"+IP+":"+PORT+"/share/admin?wsdl");
            QName qname = new QName("http://admin.silverxeon.fr/", "AdminImplService");

            Service service = Service.create(url, qname);
            inst = service.getPort(AdminInter.class);
        }catch(MalformedURLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }

        JFileChooser jf = new JFileChooser();
        jf.setSelectedFile(new java.io.File("archive.zip"));
        int returnVal = jf.showSaveDialog(null);

        try{
            DataHandler d = inst.download(session);
            InputStream i = d.getInputStream();

            //OutputStream o = new FileOutputStream(new File(fc.getSelectedFile().getAbsolutePath()));
            Files.copy(i, Paths.get(jf.getSelectedFile().getAbsolutePath()));
        }catch(Exception e){
            new JFXPanel();
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
