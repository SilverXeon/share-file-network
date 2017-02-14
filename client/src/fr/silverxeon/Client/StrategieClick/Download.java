package fr.silverxeon.Client.StrategieClick;

import fr.silverxeon.Graphics.Panneau;
import fr.silverxeon.client.ClientInter;

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
    @Override
    public void reagir() {
        System.out.println("Je dl");
    }

    public static void download(String session, Panneau p){
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
            e.getMessage();
            //TODO Alert avec session inexistante
            return;
        }

        try{
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(null);
            InputStream i = d.getInputStream();

            //OutputStream o = new FileOutputStream(new File(fc.getSelectedFile().getAbsolutePath()));
            Files.copy(i, Paths.get(fc.getSelectedFile().getAbsolutePath()));
        }catch(IOException e){
            e.getMessage();
            //Alert avec exception
        }
    }
}
