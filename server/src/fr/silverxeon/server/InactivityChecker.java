package fr.silverxeon.server;

import fr.silverxeon.session.Session;

import java.util.Map;

/**
 * Created by Pierre on 04/02/2017.
 */
public class InactivityChecker extends Thread{
    private boolean work;
    public void run(){
        while(Server.ACTIVE){
            System.out.println("Check map");
            work = true;
            for (Map.Entry<String, Session> entry : Server.listeSession.entrySet())
            {

                entry.getValue().updateLoad(System.currentTimeMillis());

                try {
                    Thread.sleep(20);
                }catch (InterruptedException e){
                    System.err.println(e.getMessage());
                }
            }
            work = false;

            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
    }

    public void arreter(){
        while(work);
        Thread.currentThread().interrupt();
    }
}
