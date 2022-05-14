package puzzle;

import puzzleWindows.MainMenu;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

import static java.util.Objects.hash;

public class Main {
    public static String idBut = "123804765";
    public static String idTest = "517304286";
    public static void main(String[] args) {
        JFrame jFrame = new MainMenu("Puzzle");
        jFrame.setBounds(450, 100, 940, 800);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
}
