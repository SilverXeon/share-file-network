package fr.silverxeon.Graphics.StrategieClick;

import fr.silverxeon.Graphics.JavaFXInit;
import fr.silverxeon.Graphics.Panneau;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Download implements StrategieClick {
    @Override
    public void reagir() {
        System.out.println("Je dl");
    }

    public static void download(int session, Panneau p){
        //Telecharger depuis le serveur et enregister sur le bureau

        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Erreur de session");
        al.setHeaderText("Session inexistante : "+session);
        al.showAndWait();
    }
}
