package fr.silverxeon.Client.StrategieClick;

import fr.silverxeon.client.ClientInter;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.swing.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOMFeature;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Upload implements StrategieClick {
    @Override
    public void reagir() {
        System.out.println("J'upload");
    }

    public static void upload(String session, String name, String surname){
        ClientInter inst = null;
        try{
            URL url = new URL("http://localhost:9999/share/client?wsdl");
            QName qname = new QName("http://client.silverxeon.fr/", "ClientImplService");

            Service service = Service.create(url, qname);
            inst = service.getPort(ClientInter.class);

            BindingProvider bp = (BindingProvider) inst;
            SOAPBinding binding = (SOAPBinding) bp.getBinding();
            binding.setMTOMEnabled(true);
        }catch(MalformedURLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        JFileChooser jf = new JFileChooser();
        int returnVal = jf.showOpenDialog(null);
        FileDataSource f = new FileDataSource(jf.getSelectedFile());
        try{
            DataHandler d = new DataHandler(f);

            inst.upload(session,d, name, surname, jf.getSelectedFile().getName().substring(jf.getSelectedFile().getName().lastIndexOf('.')+1));
        }catch(Exception e){
            System.err.println(e.getMessage());
            //TODO Alert avec exception
        }
    }
}
