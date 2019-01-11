package view.gui;

import controller.GameController;
import model.Board;
import model.Case;
import utility.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

class BoardPane extends JPanel implements Observer {
	private int dimension;

	private final MainGUI mainGUI;
	private final ArrayList<CaseGUI> cases;
	private final GameController controller;

	private Board board;

	private BufferedImage bufferedImage;
	private int imageHeight;
	private int imageWidth;

	private boolean isPlaying;

	BoardPane(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		this.dimension = 3;

		board = new Board(dimension);
		cases = new ArrayList<>();
		controller = new GameController();
		isPlaying = false;

		setPaneLayout(360);

		controller.setBoard(board);

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				Case emptyCase = board.getCase(" ");

				switch (e.getKeyCode()) {
					case 37: // left
						if (emptyCase.getCoord()[1] - 1 >= 0) {
							controller.move(board.getCases()[emptyCase.getCoord()[0]][emptyCase.getCoord()[1] - 1]);
							update();
						}
						break;
					case 38: // up
						if (emptyCase.getCoord()[0] - 1 >= 0) {
							controller.move(board.getCases()[emptyCase.getCoord()[0] - 1][emptyCase.getCoord()[1]]);
							update();
						}
						break;
					case 39: // right
						if (emptyCase.getCoord()[1] + 1 < board.getCases().length) {
							controller.move(board.getCases()[emptyCase.getCoord()[0]][emptyCase.getCoord()[1] + 1]);
							update();
						}
						break;
					case 40: // down
						if (emptyCase.getCoord()[0] + 1 < board.getCases().length) {
							controller.move(board.getCases()[emptyCase.getCoord()[0] + 1][emptyCase.getCoord()[1]]);
							update();
						}
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		initPane();
	}

	private void setPaneLayout(int size) {
		setLayout(new GridLayout(dimension, dimension));
		setSize(new Dimension(size/2 * dimension, size/2 * dimension));
	}

	private void initPane() {
		Image image = null;

		try {
			image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/images/New-Super-Mario-Bros-Art-21-400x400.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image scaledImage = Objects.requireNonNull(image).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

		bufferedImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics graphics = bufferedImage.createGraphics();
		graphics.drawImage(scaledImage, 0, 0, null);
		graphics.dispose();

		imageHeight = bufferedImage.getHeight();
		imageWidth = bufferedImage.getWidth();

		update();
	}

	void resetBoard(int dimension) {
		this.dimension = dimension;

		Case.COUNT = new AtomicInteger(0);
		board = new Board(dimension);
		controller.setBoard(board);

		cases.clear();

		update();
	}

	void resetBoard() {
		Case.COUNT = new AtomicInteger(0);
		board.initBoard();

		cases.clear();

		update();
	}

	void shuffle() {
		board.shuffleBoard();

		update();
	}

	void setPlaying(boolean playing) {
		isPlaying = playing;
	}

	@Override
	public void update() {
		removeAll();

		for (int row = 0; row < dimension; row++)
			for (int col = 0; col < dimension; col++) {
				if (cases.size() < dimension * dimension) {
					if (cases.size() == (dimension * dimension) - 1)
						cases.add(new CaseGUI(board.getCases()[row][col], null));
					else {
						cases.add(new CaseGUI(board.getCases()[row][col], bufferedImage.getSubimage((imageWidth / dimension) * col, (imageHeight / dimension) * row, imageWidth / dimension, imageHeight / dimension)));
						cases.get(cases.size() - 1).addActionListener(e -> {
							CaseGUI caseGUI = (CaseGUI) e.getSource();
							controller.move(caseGUI.getCase());

							update();
						});
					}
				} else if (cases.get((row * dimension) + col).getCase() != board.getCases()[row][col])
					for (CaseGUI caseGUI : cases)
						if (caseGUI.getCase() == board.getCases()[row][col])
							Collections.swap(cases, (row * dimension) + col, cases.indexOf(caseGUI));
			}

		setPaneLayout(cases.get(0).getIcon().getIconHeight());

		cases.forEach(caseGUI -> {
			if (isPlaying && controller.isMovable(caseGUI.getCase())) {
				caseGUI.setEnabled(true);
				caseGUI.setBorderPainted(true);
				caseGUI.setFocusPainted(true);

				caseGUI.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						caseGUI.setBorder(new LineBorder(Color.GREEN, 5));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						caseGUI.setBorder(UIManager.getBorder("Button.border"));
					}
				});
			} else {
				caseGUI.setEnabled(false);
				caseGUI.setBorderPainted(false);
				caseGUI.setFocusPainted(false);
			}

			add(caseGUI);
		});

		revalidate();
		repaint();

		mainGUI.setLocationRelativeTo(null);

		if (isPlaying && controller.isFinished())
			mainGUI.isFinished();
	}
}
