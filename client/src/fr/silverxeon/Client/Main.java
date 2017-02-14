package fr.silverxeon.Client;

import fr.silverxeon.Graphics.Fenetre;
import fr.silverxeon.Graphics.Panneau;
import fr.silverxeon.Graphics.PanneauAdmin;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Main {
    public static void main(String[] args) {
        if(args.length>0)
            if(args[0].equals("-a") || args[0].equals("--admin"))
                new Fenetre(new PanneauAdmin());
            else
                new Fenetre(new Panneau());
        else
            new Fenetre(new Panneau());
    }
}
