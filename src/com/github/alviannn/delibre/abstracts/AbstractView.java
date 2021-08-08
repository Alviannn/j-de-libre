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

    protected abstract void buildFrame();

    protected JPanel marginWrap(Component comp, int top, int left, int bottom, int right) {
        JPanel wrapped = new JPanel(new GridLayout(1, 0));

        wrapped.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        wrapped.add(comp);

        return wrapped;
    }

    protected JPanel marginWrap(Component comp, int margin) {
        return this.marginWrap(comp, margin, margin, margin, margin);
    }

    protected GridBagConstraints getDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        return gbc;
    }

    public void updateUI() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof JComponent) {
                ((JComponent) comp).updateUI();
            }
        }
    }

}
