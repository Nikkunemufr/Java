package sokoban.editor;

import sokoban.model.AbstractModel;
import sokoban.model.LevelLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Edition of levels for the Sokoban game.
 */
public class LevelEditor extends AbstractModel {

    private ArrayList<String> game;
    private int height;
    private int width;
    private char type = ' ';

    /**
     * Constructs a new Level Editor from an empty game.
     */
    public LevelEditor() {
        height = 5;
        width = 5;
        game = new ArrayList<>();
        initEditor();
    }

    /**
     * Constructs a new Level Editor from a level.
     *
     * @param level The level to be edited
     */
    public LevelEditor(LevelLoader level) {
        height = level.getHeight();
        width = level.getWidth();
        game = new ArrayList<>();
        game = level.getLevelList();
    }

    /**
     * Initialises a Level Editor with empty squares.
     */
    private void initEditor() {
        for (int x = 0; x < height; x++) {
            StringBuilder sum = new StringBuilder();
            for (int y = 0; y < width; y++) {
                sum.append(" ");
            }
            game.add(sum.toString());
        }
    }

    /**
     * Adds a row with an element of a certain type.
     *
     * @param type The type of the elements to be added
     */
    public void addRow(char type) {
        StringBuilder sum = new StringBuilder();
        for (int y = 0; y < width; y++) {
            sum.append(type);
        }
        game.add(sum.toString());
        height += 1;
        notifyObserver(this);
    }

    /**
     * Removes a row with a certain index.
     *
     * @param index The index of the row to be removed
     */
    public void removeRow(int index) {
        if (index >= 0 && index < height) {
            game.remove(index);
            height -= 1;
            notifyObserver(this);
        }
    }

    /**
     * Adds a column with an element of a certain type.
     *
     * @param type The type of the elements to be added
     */
    public void addCol(char type) {
        for (int i = 0; i < game.size(); i++)
            game.set(i, game.get(i) + type);
        width += 1;
        notifyObserver(this);
    }

    /**
     * Removes a column with a certain index.
     *
     * @param index The index of the column to be removed
     */
    public void removeCol(int index) {
        if (index >= 0 && index < width) {
            for (int i = 0; i < game.size(); i++) {
                StringBuilder sb = new StringBuilder(game.get(i));
                sb.deleteCharAt(index);
                game.set(i, sb.toString());
            }
            width -= 1;
            notifyObserver(this);
        }
    }

    /**
     * Inserts a square at a specific (row, y) coordinate
     *
     * @param type The type of the square to be inserted
     * @param row  The row index where the square must be inserted
     * @param col  The column index where the square must be inserted
     */
    public void insertSquare(char type, int row, int col) {
        StringBuilder gameRow = new StringBuilder(game.get(row));
        gameRow.setCharAt(col, type);
        game.set(row, gameRow.toString());
        notifyObserver(this);
    }

    /**
     * Saves the edited level into a file.
     *
     * @param filename The name of the file to be saved
     */
    public void save(String filename) {
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
            for (String line : game) {
                bw.write(line + "\n");
            }
            bw.close();
            fw.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    /**
     * Sets the type of this desired element.
     *
     * @param type The type of this desired element
     */
    public void setType(String type) {
        switch (type) {
            case "Empty":
                this.type = ' ';
                break;
            case "Wall":
                this.type = '#';
                break;
            case "Box":
                this.type = '$';
                break;
            case "Player":
                this.type = '@';
                break;
            case "Target":
                this.type = '.';
                break;
            case "BoxOnTarget":
                this.type = '*';
                break;
            case "PlayerOnTarget":
                this.type = '+';
                break;
        }
    }

    /**
     * Returns this game.
     *
     * @return This game
     */
    public ArrayList<String> getGame() {
        return game;
    }

    /**
     * Returns this type.
     *
     * @return This type
     */
    public char getType() {
        return type;
    }

    /**
     * Returns this height.
     *
     * @return This height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns this width.
     *
     * @return This width
     */
    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        for (String s : game) {
            System.out.println(s);
        }
        return "";
    }
}