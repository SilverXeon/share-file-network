package fr.silverxeon.server;

import fr.silverxeon.admin.AdminImpl;
import fr.silverxeon.client.ClientImpl;
import fr.silverxeon.session.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.xml.ws.Endpoint;
/**
 * Created by Pierre on 04/02/2017.
 */
public class Server {
    public static HashMap<String, Session> listeSession;
    public static String PATH = "/tmp/";
    public static int INACTIVE_DELAY = 300000;
    private static InactivityChecker inactiveCheck;
    public static boolean ACTIVE = true;

    public static void crash(){
        try{
            Scanner sc = new Scanner(new File(PATH+"crash.liste"));
            System.out.println("Crash lors de la dernière execution, récupération des sessions...");
            while(sc.hasNextLine()){
                String id = sc.nextLine();
                listeSession.put(id, new Session(id));
            }
            sc.close();
            System.out.println("Sessions récupérées");
        }catch(FileNotFoundException f){
            listeSession = new HashMap<String, Session>();

        }
    }

    public static void config(){
        try{
            Scanner sc = new Scanner(new File("config.cfg"));
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                try {
                    Field f = Server.class.getField(line.split("=")[0]);
                    String value = line.split("=")[1];
                    if(f.getType() == int.class){
                        f.set(null, Integer.parseInt(value));
                    }
                    else {
                        f.set(null, value);
                    }
                }catch(NoSuchFieldException|IllegalAccessException n){
                    System.err.println(n.getMessage());
                    System.exit(1);
                }
            }

        }catch(FileNotFoundException f){
            System.err.println("Impossible de lire la configuration : "+f.getMessage());
            System.err.println("Utilisation d'une configuration temporaire");
        }
    }

    public static void main(String[] args) {
        config();
        crash();
        AdminImpl.setListeSession(listeSession);
        ClientImpl.setListeSession(listeSession);
        Endpoint.publish("http://localhost:9999/share/admin", new AdminImpl());
        Endpoint.publish("http://localhost:9999/share/client", new ClientImpl());
        System.out.println("Ready !");
        inactiveCheck = new InactivityChecker();
        inactiveCheck.start();
        console();
    }

    public static void console(){
        boolean stop = false;
        String command = "";
        Scanner sc = new Scanner(System.in);
        do{
            System.out.print(">");
            command = sc.nextLine();
            if(command.equals("listSession")){

            }
            else if(command.equals("shutdown")){
                System.out.print("Voulez vous vraiment eteindre le serveur ? Cela effacera tout les fichiers des sessions actives (O/N) >");
                char c = sc.nextLine().charAt(0);
                if(c == 'O' || c == 'o')
                    stop = true;
                else
                    System.out.println("Extinction annulée");
            }
            else{
                System.err.println("Commande introuvable");
            }
        }while(!stop);
        ACTIVE = false;
        System.out.println("Extinction, suppression des fichiers ...");
        inactiveCheck.arreter();
        for (Map.Entry<String, Session> entry : Server.listeSession.entrySet())
        {
            entry.getValue().closeSession();
        }

        File crash = new File(Server.PATH+"/crash.liste");
        crash.delete();
        System.out.println("Terminé");
        System.exit(0);
    }
}
