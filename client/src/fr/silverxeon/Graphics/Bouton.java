package fr.silverxeon.Graphics;

import fr.silverxeon.Client.StrategieClick.StrategieClick;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Pierre on 13/02/2017.
 */
public class Bouton extends JButton implements MouseListener {
    private StrategieClick strat;
    public Bouton(String str, int x ,int y, StrategieClick strat){
        super(str);
        this.strat = strat;
        this.setLayout(null);
        this.setSize(200, 50);
        this.setLocation(x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
