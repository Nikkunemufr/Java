package model;

import utility.AbstractModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board extends AbstractModel {
	private final int dimension;

	private Case[][] cases;

	public Board(int dimension) {
		this.dimension = dimension;

		initBoard();
	}

	public void initBoard() {
		cases = new Case[dimension][dimension];

		for (int row = 0; row < dimension; row++)
			for (int col = 0; col < dimension; col++)
				cases[row][col] = new Case(this, new int[]{row, col});

		cases[dimension - 1][dimension - 1].setId(" ");
	}

	public void shuffleBoard() {
		ArrayList<Case> caseArrayList = new ArrayList<>();

		for (Case[] row : cases)
			caseArrayList.addAll(Arrays.asList(row));

		Case emptyCase = caseArrayList.get(caseArrayList.size() - 1);
		caseArrayList.remove(emptyCase);

		Collections.shuffle(caseArrayList);

		caseArrayList.add(emptyCase);

		for (int row = 0; row < cases.length; row++)
			for (int col = 0; col < cases[0].length; col++){
				cases[row][col] = caseArrayList.get((dimension * row) + col);
				caseArrayList.get((dimension * row) + col).setCoord(new int[]{row, col});
			}

		notifyObservers();
	}

	public Case getCase(String id) {
		for (Case[] row : cases)
			for (Case aCase : row)
				if (aCase.getId().equals(id))
					return aCase;

		return null;
	}

	public Case[][] getCases() {
		return cases;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();

		Boolean format = false;

		if (dimension * dimension >= 10)
			format = true;

		for (Case[] row : cases) {
			for (Case aCase : row){
				String id;

				if (format) {
					String formatter = "%" + String.valueOf(dimension * dimension).length() + "s";
					id = String.format(formatter, aCase.getId());
				} else
					id = aCase.getId();

				res.append(id).append(" ");
			}

			res.append("\n");
		}

		return String.valueOf(res);
	}
}
