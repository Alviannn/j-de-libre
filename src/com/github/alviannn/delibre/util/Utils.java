package com.github.alviannn.delibre.util;

import javax.swing.*;
import java.awt.*;

public class Utils {

    /**
     * Wraps a component with an empty border (in order to create a margin effect like in HTML/CSS)
     */
    public static JPanel marginWrap(Component comp, int top, int left, int bottom, int right) {
        JPanel wrapped = new JPanel(new GridLayout(1, 0));

        wrapped.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        wrapped.add(comp);

        return wrapped;
    }

    /**
     * Wraps a component with an empty border (in order to create a margin effect like in HTML/CSS)
     */
    public static JPanel marginWrap(Component comp, int margin) {
        return Utils.marginWrap(comp, margin, margin, margin, margin);
    }

    /**
     * Provides a default {@link GridBagConstraints}
     */
    public static GridBagConstraints getDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        return gbc;
    }

    /**
     * Determines if a string is an integer
     */
    public static boolean isInt(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

}
