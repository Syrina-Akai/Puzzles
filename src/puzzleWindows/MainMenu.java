package puzzleWindows;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{
    private JButton aProposButton;
    private JPanel MainPanel;
    private JComboBox comboBox1;

    public MainMenu (String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.pack();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(155, 168);
    }

}
