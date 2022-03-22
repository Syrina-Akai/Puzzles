package puzzle;

import puzzleWindows.MainMenu;


import javax.swing.*;

public class Main {
    public static String idBut = "123804765";

    public static void main(String[] args) {
        JFrame jFrame=new MainMenu("Puzzle");
        jFrame.setBounds(450,100,600,500);
        //jFrame.setLocationByPlatform(true);
        jFrame.setVisible(true);
    }
}
