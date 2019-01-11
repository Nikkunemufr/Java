package sokoban.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SokButton extends JButton {

    /**
     * Button colour.
     */
    private Color buttonColour = new Color(253, 114, 116);;

    /**
     * Constructs a new button.
     *
     * @param text The text to be inserted in the button
     */
    public SokButton(String text, ActionListener al) {
        setText(text);

        /* Button style */
        setOpaque(true);
        setBorderPainted(false);
        setBackground(buttonColour);
        setForeground(Color.WHITE);

        /* Customise the colour when hovering the button */
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(buttonColour.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(buttonColour);
            }
        });

        addActionListener(al);
    }

}
