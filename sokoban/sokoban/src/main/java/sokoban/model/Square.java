package sokoban.model;

public class Square implements Cloneable{

    /**
     * States that a square can take.
     */
    public final static int EMPTY = 0;
    public final static int WALL = 1;
    public final static int BOX = 2;
    public final static int PLAYER = 3;
    public final static int TARGET = 4;
    public final static int BOX_ON_TARGET = 5;
    public final static int PLAYER_ON_TARGET = 6;
    
    /**
     * Type of the square.
     */
    private int type;
    
    /**
     * State of the square.
     */
    
    private int state = 1;
    private boolean check = false;
    private int chemin = -1;
    
    /**
     * Builds a Square with a certain type.
     *
     * @param type The type of the square
     */
    public Square(int type) {
        this.type = type;
    }

    /**
     * Returns the square type.
     *
     * @return The square type
     */
    public int getType() {
        return type;
    }
    
    /**
     * Returns the square state.
     *
     * @return The square state
     */
    public int getState() {
        return state;
    }
    
    public boolean getCheck() {
        return check;
    }
    
    public int getChemin() {
    	return chemin;
    }
    
    /**
     * Returns the image filename of this square.
     *
     * @return The image filename of this square
     */
    public char getImage() {
        return (char) (type + '0');
    }

    public char getElement() {
        switch (type) {
            case Square.EMPTY:
                return ' ';
            case Square.WALL:
                return '#';
            case Square.BOX:
                return '$';
            case Square.PLAYER:
                return '@';
            case Square.TARGET:
                return '.';
            case Square.BOX_ON_TARGET:
                return '*';
            case Square.PLAYER_ON_TARGET:
                return '+';
            default:
                return ' ';
        }
    }

    /**
     * Changes the square type.
     *
     * @param type A square type
     */
    public void changeType(int type) {
        if (this.type == Square.TARGET) {
            if (type == Square.PLAYER || type == Square.PLAYER_ON_TARGET) {
                this.type = Square.PLAYER_ON_TARGET;
            } else if (type == Square.BOX || type == Square.BOX_ON_TARGET) {
                this.type = Square.BOX_ON_TARGET;
            }
        } else if (this.type == Square.EMPTY && type == Square.PLAYER_ON_TARGET) {
            this.type = Square.PLAYER;
        } else if (this.type == Square.EMPTY && type == Square.BOX_ON_TARGET) {
            this.type = Square.BOX;
        } else {
            this.type = type;
        }
    }
    
    public void changeState() {
    	state = 0;
    }
    
    public void changeState(int nbr) {
    	state = nbr;
    }
    
    public void changeCheck(boolean bin) {
    	check = bin;
    }
    
    public void changeChemin(int nbr) {
    	chemin = nbr;
    }
    
    /**
     * Copy the square.
     *
     * @return Square
     */
    public Square clone(){
    	Square copie = null;
    	
    	try {
    		copie = (Square) super.clone();
    	} catch(CloneNotSupportedException cnse){
    		cnse.printStackTrace(System.err);
    	}
    	return copie;
    }
    
}