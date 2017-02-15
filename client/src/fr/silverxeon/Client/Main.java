package fr.silverxeon.Client;

import fr.silverxeon.Graphics.Fenetre;
import fr.silverxeon.Graphics.Panneau;
import fr.silverxeon.Graphics.PanneauAdmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Main {
    public static String IP;
    public static String PORT;
    public static void config(){
        try{
            Scanner sc = new Scanner(new File("config.cfg"));
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                try {
                    Field f = Main.class.getField(line.split("=")[0]);
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
        if(args.length>0)
            if(args[0].equals("-a") || args[0].equals("--admin"))
                new Fenetre(new PanneauAdmin());
            else
                new Fenetre(new Panneau());
        else
            new Fenetre(new Panneau());
    }
}
