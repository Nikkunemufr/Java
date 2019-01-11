package view.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class MainGUI extends JFrame {
	private static BoardPane boardPane;
	private static ButtonsPane buttonsPane;

	private MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
		setTitle("Taquin");

		JPanel contentPane = new JPanel();
		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout());

		boardPane = new BoardPane(this);
		buttonsPane = new ButtonsPane(boardPane, this);

		contentPane.add(buttonsPane, BorderLayout.PAGE_START);
		contentPane.add(boardPane, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	void isFinished(){
		JDialog winDialog = new JDialog();
		JPanel winPanel = new JPanel();

		winDialog.setContentPane(winPanel);
		winDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		winDialog.setLocationRelativeTo(this);
		winDialog.setResizable(false);
		winDialog.setTitle("You won !");

		winPanel.setLayout(new BorderLayout());

		JLabel congratulationLabel = new JLabel("Congratulations, you completed the board !");
		congratulationLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> {
			winDialog.dispose();

			boardPane.setPlaying(false);
			boardPane.setFocusable(false);
			boardPane.resetBoard();

			buttonsPane.getStartButton().setEnabled(true);
			buttonsPane.getResetButton().setEnabled(false);
			buttonsPane.getSizeChooser().setEnabled(true);
			buttonsPane.getValidateButton().setEnabled(true);
		});

		winPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		winPanel.add(congratulationLabel, BorderLayout.CENTER);
		winPanel.add(closeButton, BorderLayout.PAGE_END);

		winDialog.pack();

		winDialog.setVisible(true);
	}

	public static void main(String[] args) {
		new MainGUI();
	}
}
