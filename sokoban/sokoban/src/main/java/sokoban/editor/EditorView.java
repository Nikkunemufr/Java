package sokoban.editor;

import javax.swing.*;

import java.awt.*;

public class EditorView extends JFrame {

    private JPanel master = new JPanel();

    public EditorView(LevelEditor level) {
        /* Title of the frame */
        setTitle("Sokoban Editor");

        /* Size of the frame */
        setSize(1000, 1000);

        /* The program disposes when the cross is clicked */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* Position of the frame in the screen */
        setLocationRelativeTo(null);  // centering

        /* Add the button on the master */
        setContentPane(master);

        /* Set the layout of the JFrame and the master pane */
        setLayout(null);
        master.setLayout(new FlowLayout());

        ImageIcon emptyIcon = new ImageIcon("src/main/resources/images/theme0/0.png");
        JButton emptyButton = new JButton(emptyIcon);
        emptyButton.addActionListener(e -> level.setType("Empty"));

        ImageIcon wallIcon = new ImageIcon("src/main/resources/images/theme0/1.png");
        JButton wallButton = new JButton(wallIcon);
        wallButton.addActionListener(e -> level.setType("Wall"));

        ImageIcon boxIcon = new ImageIcon("src/main/resources/images/theme0/2.png");
        JButton boxButton = new JButton(boxIcon);
        boxButton.addActionListener(e -> level.setType("Box"));

        ImageIcon playerIcon = new ImageIcon("src/main/resources/images/theme0/3.png");
        JButton playerButton = new JButton(playerIcon);
        playerButton.addActionListener(e -> level.setType("Player"));

        ImageIcon targetIcon = new ImageIcon("src/main/resources/images/theme0/4.png");
        JButton targetButton = new JButton(targetIcon);
        targetButton.addActionListener(e -> level.setType("Target"));

        ImageIcon boxOnTargetIcon = new ImageIcon("src/main/resources/images/theme0/5.png");
        JButton boxOnTargetButton = new JButton(boxOnTargetIcon);
        boxOnTargetButton.addActionListener(e -> level.setType("BoxOnTarget"));

        ImageIcon playerOnTargetIcon = new ImageIcon("src/main/resources/images/theme0/6.png");
        JButton playerOnTargetButton = new JButton(playerOnTargetIcon);
        playerOnTargetButton.addActionListener(e -> level.setType("PlayerOnTarget"));

        JButton addRowButton = new JButton("Add Row");
        addRowButton.addActionListener(e -> level.addRow(level.getType()));

        JButton addColButton = new JButton("Add Column");
        addColButton.addActionListener(e -> level.addCol(level.getType()));


        JLabel indexRow = new JLabel("Index :");
        TextField txtFieldRow = new TextField();
        JButton removeRowButton = new JButton("Remove Row");
        removeRowButton.addActionListener(e -> level.removeRow(Integer.parseInt(txtFieldRow.getText())));

        JLabel indexCol = new JLabel("Index :");
        TextField txtFieldCol = new TextField();
        JButton removeColButton = new JButton("Remove Column");
        removeColButton.addActionListener(e -> level.removeCol(Integer.parseInt(txtFieldCol.getText())));

        JLabel filenameSave = new JLabel("Filename :");
        TextField txtFieldSave = new TextField();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> level.save(txtFieldSave.getText()));

        // Add button to JPanel
        master.add(emptyButton);
        master.add(wallButton);
        master.add(boxButton);
        master.add(playerButton);
        master.add(targetButton);
        master.add(boxOnTargetButton);
        master.add(playerOnTargetButton);
        master.add(addRowButton);
        master.add(addColButton);


        /* Board sokoban.view (JPanel) */
        EditorFrame editorFrame = new EditorFrame(level);
        editorFrame.displayFrame();
        master.add(editorFrame);

        master.add(removeRowButton);
        master.add(indexRow);
        master.add(txtFieldRow);
        master.add(removeColButton);
        master.add(indexCol);
        master.add(txtFieldCol);
        master.add(saveButton);
        master.add(filenameSave);
        master.add(txtFieldSave);
    }

}