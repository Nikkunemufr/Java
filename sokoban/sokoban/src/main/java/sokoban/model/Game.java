package sokoban.model;

import java.util.ArrayList;

public class Game extends AbstractModel {

    /**
     * The board representation of this game.
     */
    public Square[][] board;

    /**
     * The list of the player coordinates.
     */
    private ArrayList<Integer> player = new ArrayList<>();

    /**
     * The level to be loaded by this game.
     */
    public LevelLoader level;

    /**
     * The counter that checks the game has ended.
     */
    public int counter;

    /**
     * Constructs a new Game.
     *
     * @param level The level to be loaded by the game.
     */
    public Game(LevelLoader level) {
        this.level = level;
        board = new Square[level.getHeight()][level.getWidth()];
        player.add(0);
        player.add(0);
        initBoard(level);
    }

    /**
     * Initialises the board according to a sokoban.level.
     *
     * @param level The sokoban.level to be parsed
     */
    public void initBoard(LevelLoader level) {
        for (int x = 0; x < level.getHeight(); x++) {
            for (int y = 0; y < level.getWidth(x); y++) {
                switch (level.getElement(x, y)) {
                    case ' ':
                        board[x][y] = new Square(Square.EMPTY);
                        break;
                    case '#':
                        board[x][y] = new Square(Square.WALL);
                        break;
                    case '$':
                        board[x][y] = new Square(Square.BOX);
                        counter++;
                        break;
                    case '@':
                        player.set(0, x);
                        player.set(1, y);
                        board[x][y] = new Square(Square.PLAYER);
                        break;
                    case '.':
                        board[x][y] = new Square(Square.TARGET);
                        break;
                    case '*':
                        board[x][y] = new Square(Square.BOX_ON_TARGET);
                        break;
                    case '+':
                        player.set(0, x);
                        player.set(1, y);
                        board[x][y] = new Square(Square.PLAYER_ON_TARGET);
                        break;
                }
            }
        }
    }

    /**
     * Returns a square next to the player by (row, col).
     *
     * @param row The row number (-1, 0 or 1) next to the player
     * @param col The col number (-1, 0 or 1) next to the player
     * @return A square next to the player by (row, col)
     */
    private Square getNextPlayerSquare(int row, int col) {
        return board[player.get(0) + row][player.get(1) + col];
    }

    /**
     * Returns the square corresponding to the (row, col) coordinates
     *
     * @param row The row index of the square to be returned
     * @param col The col index of the square to be returned
     * @return The square corresponding to the (row, col) coordinates
     */
    private Square getSquare(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns the player square.
     *
     * @return The player square
     */
    private Square getPlayerSquare() {
        return getSquare(player.get(0), player.get(1));
    }
    
    public int getPlayerx() {
        return player.get(0);
    }

    public int getPlayery() {
        return player.get(1);
    }
    
    /**
     * Moves the player according to a given direction row, col.
     *
     * @param row The row direction
     * @param col The col direction
     */
    public void move(int row, int col) {
        ArrayList<Integer> nextPosition = new ArrayList<>();

        nextPosition.add(player.get(0) + row);
        nextPosition.add(player.get(1) + col);

        /* If the square next to the player is a target or is empty */
        if (getNextPlayerSquare(row, col).getType() == Square.TARGET
                || getNextPlayerSquare(row, col).getType() == Square.EMPTY) {

            /* Move the player */
            switchSquares(getPlayerSquare(), getNextPlayerSquare(row, col));
            player.set(0, nextPosition.get(0));
            player.set(1, nextPosition.get(1));
        } else {

            /*
             * If the square next to the player is a box and if the square next to the next
             * position is empty or is a target
             */
            if (getNextPlayerSquare(row, col).getType() == Square.BOX
                    && isUnoccupied(getNextPlayerSquare(row * 2, col * 2))) {
                counter = isTarget(getNextPlayerSquare(row, col)) ? counter-- : counter;
                switchSquares(getNextPlayerSquare(row, col), getNextPlayerSquare(row * 2, col * 2));
                switchSquares(getPlayerSquare(), getNextPlayerSquare(row, col));
                player.set(0, nextPosition.get(0));
                player.set(1, nextPosition.get(1));
            }

            /*
             * If the square next to the player is box on a target and if the
             * square next to the box on a target is empty or is a target
             */
            else if (board[nextPosition.get(0)][nextPosition.get(1)].getType() == Square.BOX_ON_TARGET
                    && isUnoccupied(getNextPlayerSquare(row * 2, col * 2))) {
                counter = isEmpty(getNextPlayerSquare(row, col)) ? counter++ : counter;
                switchSquares(getNextPlayerSquare(row, col), getNextPlayerSquare(row * 2, col * 2));
                switchSquares(getPlayerSquare(), getNextPlayerSquare(row, col));
                player.set(0, nextPosition.get(0));
                player.set(1, nextPosition.get(1));
            }
        }
        notifyObserver(this);
    }
    
    public ArrayList<Integer> move(int x, int y, Square[][] newboard, ArrayList<Integer> position) {
        ArrayList<Integer> nextPosition = new ArrayList<>();

        nextPosition.add(position.get(0) + x);
        nextPosition.add(position.get(1) + y);
        if (newboard[nextPosition.get(0)][nextPosition.get(1)].getType() == Square.TARGET || newboard[nextPosition.get(0)][nextPosition.get(1)].getType() == Square.EMPTY) {
            swap(position.get(0), position.get(1), nextPosition.get(0), nextPosition.get(1), newboard);
        	return nextPosition;
        } else {
            if (newboard[nextPosition.get(0)][nextPosition.get(1)].getType() == Square.BOX && nextSquare(position.get(0) + 2 * x, position.get(1) + 2 * y, newboard)){
            	counter = (newboard[nextPosition.get(0) + x][nextPosition.get(1) + y].getType() == Square.TARGET) ? counter - 1 : counter;
            	swap(nextPosition.get(0), nextPosition.get(1), nextPosition.get(0) + x, nextPosition.get(1) + y, newboard);
                swap(position.get(0), position.get(1), nextPosition.get(0), nextPosition.get(1), newboard);
            	return nextPosition;

            } else if (newboard[nextPosition.get(0)][nextPosition.get(1)].getType() == Square.BOX_ON_TARGET && nextSquare(position.get(0) + 2 * x, position.get(1) + 2 * y, newboard)){
            	counter = (newboard[nextPosition.get(0) + x][nextPosition.get(1) + y].getType() == Square.EMPTY) ? counter + 1 : counter;
            	swap(nextPosition.get(0), nextPosition.get(1), nextPosition.get(0) + x, nextPosition.get(1) + y, newboard);
                swap(position.get(0), position.get(1), nextPosition.get(0), nextPosition.get(1), newboard);
            	return nextPosition;
            }
        }
        
    	return position;
    }
    
    public void swap(int x1, int y1, int x2, int y2, Square[][] newboard) {
        int tampon = newboard[x1][y1].getType();
        if ((newboard[x1][y1].getType() == Square.PLAYER_ON_TARGET || newboard[x1][y1].getType() == Square.BOX_ON_TARGET) && newboard[x2][y2].getType() != Square.WALL) {
            newboard[x1][y1].changeType(Square.TARGET);
        } else {
            newboard[x1][y1].changeType(Square.EMPTY);
        }
        newboard[x2][y2].changeType(tampon);
    }

    public boolean nextSquare(int pos1, int pos2, Square[][] newboard) {
        return (newboard[pos1][pos2].getType() == Square.EMPTY) || (newboard[pos1][pos2].getType() == Square.TARGET);
    }


    /**
     * Swaps two squares.
     *
     * @param square1 The first square to be swapped
     * @param square2 The second square to be swapped
     */
    private void switchSquares(Square square1, Square square2) {
        int temp = square1.getType();
        if ((square1.getType() == Square.PLAYER_ON_TARGET || square1.getType() == Square.BOX_ON_TARGET) && square2.getType() != Square.WALL) {
            square1.changeType(Square.TARGET);
        } else {
            square1.changeType(Square.EMPTY);
        }
        square2.changeType(temp);
    }

    /**
     * Returns {@code true} if the square is unoccupied, i.e. if the square is
     * empty or is a target.
     *
     * @param square The square to be tested
     * @return {@code true} if the square is unoccupied; {@code false} otherwise
     */
    private boolean isUnoccupied(Square square) {
        return isEmpty(square) || isTarget(square);
    }

    /**
     * Returns {@code true} if the square is empty.
     *
     * @param square The square to be tested
     * @return {@code true} if the square is empty; {@code false} otherwise
     */
    private boolean isEmpty(Square square) {
        return square.getType() == Square.EMPTY;
    }

    /**
     * Returns {@code true} if the square is a target.
     *
     * @param square The square to be tested
     * @return {@code true} if the square is a target; {@code false} otherwise
     */
    private boolean isTarget(Square square) {
        return square.getType() == Square.TARGET;
    }

    /**
     * Returns true if this game is ended.
     */
    public boolean hasEnded() {
        return counter <= 0;
    }

    
    public boolean hasEnded(Square[][] newboard) {
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) { 
            	if (newboard[i][j].getType() == Square.BOX){
            		return false;
            	}
            }
        }
        
    	return true;
    }

    /* Accessors */

    /**
     * Returns the board of this game.
     *
     * @return The board of this game
     */
    public Square[][] getBoard() {
        return board;
    }

    /**
     * Returns the height of this game.
     *
     * @return The height of this game
     */
    public int getHeight() {
        return board.length;
    }

    /**
     * Returns the width of this game.
     *
     * @return The width of this game
     */
    public int getWidth() {
        return board[0].length;
    }

    /**
     * Returns the string representation of this game.
     *
     * @return The formatted board string
     */
    @Override
    public String toString() {
        StringBuilder formattedBoard = new StringBuilder();
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                formattedBoard.append(board[i][j].getElement());
            }
            formattedBoard.append("\n");
        }
        return formattedBoard.toString();
    }
    
    public String formatBoard(Square[][] newboard) {
        StringBuilder formattedBoard = new StringBuilder();
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                formattedBoard.append(newboard[i][j].getElement());
            }
            formattedBoard.append("\n");
        }
        return formattedBoard.toString();
    }
    
    public void printBoard(Square[][] newboard){
        System.out.println(formatBoard(newboard));
    }
    

    public void afficheRadar(Square[][] newboard){
    	for (int i = 0; i < level.getHeight(); i++) {
    		for (int j = 0; j < level.getWidth(); j++) {
			if (newboard[i][j].getChemin() == -1){
				System.out.print(newboard[i][j].getElement());
			} else {
				System.out.print(newboard[i][j].getChemin());
			}
		}
    		System.out.println("");
	}
    }

    public void afficheCheck(Square[][] newboard){
    	for (int i = 0; i < level.getHeight(); i++) {
    		for (int j = 0; j < level.getWidth(); j++) {
    			if (newboard[i][j].getCheck() == true){
    				System.out.print("1");
    			} else{
    				System.out.print(newboard[i][j].getElement());
    			}
    		}
    		System.out.println("");
    	}
    }
    
    public void afficheDeadlock(Square[][] newboard){
    	for (int i = 0; i < level.getHeight(); i++) {
    		for (int j = 0; j < level.getWidth(); j++) {
    			if (newboard[i][j].getState() == 0){
    				System.out.print("0");
    			} else if (newboard[i][j].getState() == 2){
    				System.out.print("2");
    			} else if (newboard[i][j].getState() == 1){
    				System.out.print(newboard[i][j].getElement());
    			} else {
    				System.out.print(newboard[i][j].getState());
    			}
    		}
    		System.out.println("");
    	}
    }
}
