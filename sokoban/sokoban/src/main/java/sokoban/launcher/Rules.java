package sokoban.launcher;

import sokoban.gui.SokButton;

import javax.swing.*;
import java.awt.*;

public class Rules extends JFrame {

    private BackgroundPanel master = new BackgroundPanel("/images/rules.png");

    private GridBagConstraints gbc = new GridBagConstraints();


    public Rules() {
        /* Title of the frame */
        setTitle("Sokoban - How to Play");

        /* Size of the frame */
        setSize(550, 800);

        /* The program stops when the cross is clicked */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* The frame is resizable or not */
        setResizable(false);

        /* Position of the frame in the screen */
        setLocationRelativeTo(null);  // centering the frame

        /* Set master layout to GridBagLayout for vertical centering */
        master.setLayout(new BorderLayout());

        addButtons();


        setContentPane(master);
    }

    private void addButtons() {

        JPanel buttons = new JPanel();

        buttons.setOpaque(false); // background transparency

        buttons.add(new SokButton("Return to Menu", e -> returnMenu()));

        master.add(buttons, BorderLayout.SOUTH);
    }

    /**
     * Disposes this frame.
     */
    private void returnMenu() {
        dispose();
    }
}
