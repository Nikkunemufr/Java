package view.console;

import controller.GameController;
import model.Board;
import model.Case;
import utility.Observer;

import java.util.Scanner;

public class Main implements Observer {
	private final Board board;

	private Main(Board board) {
		this.board = board;
		board.addObserver(this);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the grid dimension : ");

		int dimension;

		do {
			dimension = scanner.nextInt();
		} while (dimension <= 1);

		Board board = new Board(dimension);
		Main main = new Main(board);
		GameController controller = new GameController();
		controller.setBoard(main.board);

		System.out.println("\n\n\n" +board);

		board.shuffleBoard();

		while (!controller.isFinished()) {
			System.out.println("Select case :");
			Case selectedCase;

			do {
				selectedCase = board.getCase(scanner.next());
			} while (!controller.isMovable(selectedCase));

			controller.move(selectedCase);
		}

		System.out.println("\n\n\nBoard completed successfully ! Well done !");
	}

	@Override
	public void update() {
		System.out.println("\n" + board);
	}
}
