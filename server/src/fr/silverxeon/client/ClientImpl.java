package fr.silverxeon.client;

/**
 * Created by Pierre on 04/02/2017.
 */

import fr.silverxeon.session.Session;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.crypto.Data;
import java.util.HashMap;

@WebService(endpointInterface = "fr.silverxeon.client.ClientInter")
public class ClientImpl implements ClientInter{
    private static HashMap<String, Session> listeSession;

    public static void setListeSession(HashMap<String, Session> listeSession) {
        ClientImpl.listeSession = listeSession;
    }

    @Override
    public DataHandler download(String session) throws Exception {
        if(!listeSession.containsKey(session))
            throw new Exception("Session inexistante");
        return listeSession.get(session).getFile();
    }

    @Override
    public void upload(String session,DataHandler file, String name, String surname, String ext) throws Exception{
        System.out.println("On ajoute");
        if(!listeSession.containsKey(session))
            throw new Exception("Session inexistante");
        System.out.println("???");
        listeSession.get(session).addFileToZip(file, name, surname, ext);

    }
}
