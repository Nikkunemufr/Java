package sokoban.model;

import java.io.*;
import java.util.ArrayList;

public class LevelLoader {

    private ArrayList<String> levelList;
    private int height;
    private int width;

    /**
     * Loads a level.
     * <p>
     * Reads a file and progressively put the read elements in a list.
     *
     * @param filename The level file name
     */
    public void loadLevel(String filename) {
        levelList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filename);
            //FileReader fr = new FileReader(new File(getClass().getResource(filename).toString()));
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (br.ready()) {
                line = br.readLine();
                levelList.add(line);
            }
            br.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
        setMaxWidth(levelList);
    }

    /**
     * Transforms the board into a string list
     *
     * @param game A game
     */
    public ArrayList<String> boardToString(Game game) {
        ArrayList<String> boardList = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        Square[][] board = game.getBoard();
        for (int x = 0; x < getHeight(); x++) {
            for (int y = 0; y < getWidth(); y++) {
                if (board[x][y].getType() == Square.EMPTY) {
                    line.append(" ");
                } else if (board[x][y].getType() == Square.WALL) {
                    line.append("#");
                } else if (board[x][y].getType() == Square.BOX) {
                    line.append("$");
                } else if (board[x][y].getType() == Square.PLAYER) {
                    line.append("@");
                } else if (board[x][y].getType() == Square.TARGET) {
                    line.append(".");
                } else if (board[x][y].getType() == Square.BOX_ON_TARGET) {
                    line.append("*");
                } else if (board[x][y].getType() == Square.PLAYER_ON_TARGET) {
                    line.append("+");
                }
            }
            boardList.add(line.toString());
        }
        return boardList;
    }

    /**
     * Backs up the board.
     *
     * @param game     The game to be backed up
     * @param filename The name of the backup file
     */
    public static void saveLevel(Game game, String filename) {
        File f = new File("src/main/resources/saves/" + filename + ".sok");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(game.toString());
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the maximal width to the level width
     *
     * @param level The level list to be parsed
     */
    public void setMaxWidth(ArrayList<String> level) {
        height = level.size();
        width = 0;
        for (int i = 0; i < getHeight(); i++) {
            if (level.get(i).length() > getWidth()) {
                setWidth(level.get(i).length());
            }
        }
    }

    /**
     * Returns the character with the position (row, col) in the level list.
     *
     * @param row The row index
     * @param col The column index
     * @return The character with the position (row, col) in the level list
     */
    public char getElement(int row, int col) {
        return levelList.get(row).charAt(col);
    }

    /**
     * Returns this level height.
     *
     * @return This level height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets this level height.
     *
     * @param height A height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns this level width.
     *
     * @return This level width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the length of the level sublist in {@code x} position.
     *
     * @param x The line number
     * @return The length of the level sublist in {@code x} position
     */
    public int getWidth(int x) {
        return levelList.get(x).length();
    }

    /**
     * Sets this level width.
     *
     * @param width A width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the list of this level.
     *
     * @return The list of this level
     */
    public ArrayList<String> getLevelList() {
        return levelList;
    }
}
