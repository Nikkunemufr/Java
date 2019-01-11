package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Case {
	public static AtomicInteger COUNT = new AtomicInteger(0);
	private String id;

	private final Board board;
	private int[] coord;

	Case(Board board, int[] coord) {
		this.board = board;
		this.coord = coord;

		id = String.valueOf(COUNT.getAndIncrement());
	}

	public void move(Direction direction){
		Case tmp;
		switch (direction) {
			case UP:
				tmp = board.getCases()[coord[0] - 1][coord[1]];
				board.getCases()[coord[0] - 1][coord[1]] = this;
				board.getCases()[coord[0]][coord[1]] = tmp;

				board.getCases()[coord[0]][coord[1]].setCoord(new int[]{coord[0], coord[1]});
				board.getCases()[coord[0] - 1][coord[1]].setCoord(new int[]{coord[0] - 1, coord[1]});

				board.notifyObservers();
				break;
			case DOWN:
				tmp = board.getCases()[coord[0] + 1][coord[1]];
				board.getCases()[coord[0] + 1][coord[1]] = this;
				board.getCases()[coord[0]][coord[1]] = tmp;

				board.getCases()[coord[0]][coord[1]].setCoord(new int[]{coord[0], coord[1]});
				board.getCases()[coord[0] + 1][coord[1]].setCoord(new int[]{coord[0] + 1, coord[1]});

				board.notifyObservers();
				break;
			case LEFT:
				tmp = board.getCases()[coord[0]][coord[1] - 1];
				board.getCases()[coord[0]][coord[1] - 1] = this;
				board.getCases()[coord[0]][coord[1]] = tmp;

				board.getCases()[coord[0]][coord[1]].setCoord(new int[]{coord[0], coord[1]});
				board.getCases()[coord[0]][coord[1] - 1].setCoord(new int[]{coord[0], coord[1] - 1});

				board.notifyObservers();
				break;
			case RIGHT:
				tmp = board.getCases()[coord[0]][coord[1] + 1];
				board.getCases()[coord[0]][coord[1] + 1] = this;
				board.getCases()[coord[0]][coord[1]] = tmp;

				board.getCases()[coord[0]][coord[1]].setCoord(new int[]{coord[0], coord[1]});
				board.getCases()[coord[0]][coord[1] + 1].setCoord(new int[]{coord[0], coord[1] + 1});

				board.notifyObservers();
				break;
		}
	}

	public String getId() {
		return id;
	}

	void setId(String id) {
			this.id = id;
	}

	public int[] getCoord() {
		return coord;
	}

	void setCoord(int[] coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return "Case{" +
				"id=" + id +
				"\tcoord=(" + coord[0] + ";" + coord[1] + ")" +
				'}';
	}
}
