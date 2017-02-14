package fr.silverxeon.Client.StrategieClick.Admin;

import fr.silverxeon.admin.AdminInter;

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

/**
 * Created by Pierre on 14/02/2017.
 */
public class Session {
    public static String creerSession(){
        AdminInter inst = null;
        try{
            URL url = new URL("http://localhost:9999/share/admin?wsdl");
            QName qname = new QName("http://admin.silverxeon.fr/", "AdminImplService");
            System.out.println(qname);
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
        return inst.upload(d);
    }

    public static void recupZip(String session){
        AdminInter inst = null;
        try{
            URL url = new URL("http://localhost:9999/share/admin?wsdl");
            QName qname = new QName("http://admin.silverxeon.fr/", "AdminImplService");

            Service service = Service.create(url, qname);
            inst = service.getPort(AdminInter.class);
        }catch(MalformedURLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }

        JFileChooser jf = new JFileChooser();
        int returnVal = jf.showSaveDialog(null);

        try{
            DataHandler d = inst.download(session);
            InputStream i = d.getInputStream();

            //OutputStream o = new FileOutputStream(new File(fc.getSelectedFile().getAbsolutePath()));
            Files.copy(i, Paths.get(jf.getSelectedFile().getAbsolutePath()));
        }catch(Exception e){
            //TODO Alert exception
        }
    }
}