package fr.silverxeon.admin;

/**
 * Created by Pierre on 04/02/2017.
 */

import fr.silverxeon.session.Session;

import javax.activation.DataHandler;
import javax.jws.WebService;
import java.util.HashMap;
import java.util.Random;

@WebService(endpointInterface = "fr.silverxeon.admin.AdminInter")
public class AdminImpl implements AdminInter {
    private static HashMap<String, Session> listeSession;

    public static void setListeSession(HashMap<String, Session> listeSession) {
        AdminImpl.listeSession = listeSession;
    }

    @Override
    public String upload(DataHandler file) {
        Random r = new Random(System.currentTimeMillis());
        String sessionId;
        do {

            sessionId = String.format("%04d", Math.abs(r.nextInt()%10000));
        } while (listeSession.containsKey(sessionId));

        listeSession.put(sessionId, new Session(sessionId, file));
        return sessionId;
    }

    @Override
    public DataHandler download(String session) throws Exception {
        if (!listeSession.containsKey(session))
            throw new Exception("Session inexistante");
        return listeSession.get(session).getZip();
    }
}
