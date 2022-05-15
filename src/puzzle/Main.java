package puzzle;

import puzzleWindows.MainMenu;

import javax.swing.*;

public class Main {
    public static String idBut = "123804765";
    public static void main(String[] args) {
        JFrame jFrame = new MainMenu("Puzzle");
        jFrame.setBounds(130, 70, 940, 660);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
}
