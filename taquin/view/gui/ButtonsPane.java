package view.gui;

import javax.swing.*;
import java.awt.*;

class ButtonsPane extends JPanel {
	private final JButton startButton;
	private final JButton resetButton;
	private final JComboBox<? extends String> sizeChooser;
	private final JButton validateButton;

	ButtonsPane(BoardPane boardPane, MainGUI mainGUI) {
		setLayout(new BorderLayout());

		JPanel startResetPane = new JPanel();
		JPanel sizePane = new JPanel();

		startButton = new JButton("Start");
		resetButton = new JButton("Reset");
		validateButton = new JButton("OK");

		String[] items = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
		sizeChooser = new JComboBox<>(items);

		resetButton.setEnabled(false);

		startButton.addActionListener(e -> {
			startButton.setEnabled(false);
			resetButton.setEnabled(true);

			boardPane.setPlaying(true);
			boardPane.setFocusable(true);
			boardPane.requestFocus();
			boardPane.shuffle();

			sizeChooser.setEnabled(false);
			validateButton.setEnabled(false);
		});

		resetButton.addActionListener(e -> {
			startButton.setEnabled(true);
			resetButton.setEnabled(false);

			boardPane.setPlaying(false);
			boardPane.setFocusable(false);
			boardPane.resetBoard();

			sizeChooser.setEnabled(true);
			validateButton.setEnabled(true);
		});

		validateButton.addActionListener(e -> {
			boardPane.setPlaying(false);
			boardPane.setFocusable(false);
			boardPane.resetBoard(Integer.parseInt(String.valueOf(sizeChooser.getSelectedItem())));

			mainGUI.pack();
		});

		startResetPane.add(startButton);
		startResetPane.add(resetButton);

		sizePane.add(new JLabel("Size :"));
		sizePane.add(sizeChooser);
		sizePane.add(validateButton);

		add(startResetPane, BorderLayout.LINE_START);
		add(sizePane, BorderLayout.LINE_END);
	}

	JButton getStartButton() {
		return startButton;
	}

	JButton getResetButton() {
		return resetButton;
	}

	JComboBox<? extends String> getSizeChooser() {
		return sizeChooser;
	}

	JButton getValidateButton() {
		return validateButton;
	}
}
