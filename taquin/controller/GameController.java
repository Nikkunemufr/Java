package controller;

import model.Board;
import model.Case;
import model.Direction;

import java.util.ArrayList;

public class GameController {
	private Board board;

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isMovable(Case aCase) {
		if (aCase.getId().equals(" "))
			return false;
		if (aCase.getCoord()[0] - 1 >= 0 && board.getCases()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getId().equals(" "))
			return true;
		else if (aCase.getCoord()[0] + 1 < board.getCases().length && board.getCases()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getId().equals(" "))
			return true;
		else if (aCase.getCoord()[1] - 1 >= 0 && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getId().equals(" "))
			return true;
		else
			return aCase.getCoord()[1] + 1 < board.getCases()[0].length && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getId().equals(" ");

	}

	public void move(Case aCase) {
		if (aCase.getCoord()[0] - 1 >= 0 && board.getCases()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getId().equals(" "))
			aCase.move(Direction.UP);
		else if (aCase.getCoord()[0] + 1 < board.getCases().length && board.getCases()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getId().equals(" "))
			aCase.move(Direction.DOWN);
		else if (aCase.getCoord()[1] - 1 >= 0 && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getId().equals(" "))
			aCase.move(Direction.LEFT);
		else if (aCase.getCoord()[1] + 1 < board.getCases()[0].length && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getId().equals(" "))
			aCase.move(Direction.RIGHT);
	}

	public boolean isFinished() {
		ArrayList<Boolean> goodPlacements = new ArrayList<>();

		if (board.getCases()[board.getCases().length - 1][board.getCases().length - 1].getId().equals(" "))
			goodPlacements.add(true);
		else
			goodPlacements.add(false);

		for (int row = 0; row < board.getCases().length; row++)
			for (int col = 0; col < board.getCases()[row].length; col++)
				if (!board.getCases()[row][col].getId().equals(" "))
					if (board.getCases()[row][col].getId().equals(String.valueOf((board.getCases().length * row) + col)))
						goodPlacements.add(true);
					else
						goodPlacements.add(false);

		return goodPlacements.stream().allMatch(e -> e);
	}
}
