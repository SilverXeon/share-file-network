package fr.silverxeon.Graphics;

import javax.swing.*;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Fenetre extends JFrame {
    private Panneau pan = new Panneau();
    public Fenetre(){
        this.setTitle("Partager un fichier");
        this.setSize(300, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(pan);

        this.setVisible(true);

        go();
    }

    private void go(){
        while(true){
            pan.checkError();
            pan.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
