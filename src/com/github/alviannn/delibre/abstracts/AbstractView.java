package com.github.alviannn.delibre.abstracts;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JFrame {

    public AbstractView(int width, int height) {
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("De Libre");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        this.buildFrame();
    }

    public AbstractView() {
        this(600, 600);
    }

    /**
     * Builds the view frame into a complete usable frame
     */
    protected abstract void buildFrame();

    /**
     * Updates the entire UI components
     */
    public void updateUI() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof JComponent) {
                ((JComponent) comp).updateUI();
            }
        }
    }

}
