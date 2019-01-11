package sokoban;

import sokoban.launcher.Launcher;

public class Main {

    public static void main(String[] args) {

        if (args.length != 0) {
            System.err.println("Usage: " + Main.class.getCanonicalName());
            System.exit(1);
        } else {
            new Launcher().setVisible(true);
        }

    }

}
