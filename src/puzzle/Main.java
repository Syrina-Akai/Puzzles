package puzzle;

import puzzleWindows.MainMenu;

import javax.swing.*;
import java.util.Stack;

public class Main {
    public static String idBut = "123804765";

    public static void main(String[] args) {
        JFrame jFrame = new MainMenu("Puzzle");
        jFrame.setBounds(450, 100, 860, 600);
        jFrame.setVisible(true);
        /*
        Taquin root = new Taquin(true);
        root.idToTaquin("123784065");
        new Profondeur().solve(root, 3);*/
        /*Taquin root = new Taquin(true);
        root.idToTaquin("283164705");

        new Largeur().solve(root);*/
    }
}
