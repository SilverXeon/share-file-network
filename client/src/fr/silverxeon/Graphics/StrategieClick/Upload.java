package fr.silverxeon.Graphics.StrategieClick;

import fr.silverxeon.Graphics.Panneau;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Upload implements StrategieClick {
    @Override
    public void reagir() {
        System.out.println("J'upload");
    }

    public static void upload(int session, Panneau p){
        //Demander de choisir un fichier et l'uploader
    }
}
