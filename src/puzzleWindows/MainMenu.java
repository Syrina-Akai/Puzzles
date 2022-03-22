package puzzleWindows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.Color;

public class MainMenu extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu("Puzzle");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainMenu(String title ) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 462);
		getContentPane().setLayout(null);
		
		JPanel taquin = new JPanel();
		taquin.setBounds(220, 47, 243, 209);
		taquin.setLayout(new GridLayout(3,3));
		JButton btn1 = new JButton("1");    
		btn1.setBackground(new Color(179, 229, 252));
		JButton btn2 = new JButton("2");    
		btn2.setBackground(new Color(179, 229, 252));
		JButton btn3 = new JButton("3"); 
		btn3.setBackground(new Color(179, 229, 252));
		JButton btn4 = new JButton("4"); 
		btn4.setBackground(new Color(179, 229, 252));
		JButton btn5 = new JButton("5");  
		btn5.setBackground(new Color(179, 229, 252));
		JButton btn6 = new JButton("6"); 
		btn6.setBackground(new Color(179, 229, 252));
		JButton btn7 = new JButton("7"); 
		btn7.setBackground(new Color(179, 229, 252));
		JButton btn8 = new JButton("8");
		btn8.setBackground(new Color(179, 229, 252));
		taquin.add(btn1); taquin.add(btn2); taquin.add(btn3);
		taquin.add(btn4); taquin.add(btn5); taquin.add(btn6);
		taquin.add(btn7); taquin.add(btn8);
		getContentPane().add(taquin);		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(77, 130, 203));
		panel.setBounds(0, 0, 142, 425);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton profondeur = new JButton("Profondeur");
		profondeur.setBounds(10, 33, 121, 21);
		profondeur.setBackground(new Color(141, 204, 255));
		panel.add(profondeur);
		
		JButton largeur = new JButton("Largeur");
		largeur.setBackground(new Color(141, 204, 255));
		largeur.setBounds(10, 79, 121, 21);
		panel.add(largeur);
		
		JComboBox Heuristique = new JComboBox();
		//Heuristique.setForeground(new Color(182, 227, 255));
		Heuristique.setBackground(new Color(141, 204, 255));
		Heuristique.setBounds(10, 126, 121, 21);
		panel.add(Heuristique);
		Heuristique.setModel(new DefaultComboBoxModel(new String[] {"Manhatten", "Hamming"}));
		
		JButton aPropos = new JButton("A propos");
		aPropos.setBackground(new Color(141, 204, 255));
		aPropos.setBounds(25, 360, 85, 21);
		panel.add(aPropos);
		
		JLabel lblNewLabel = new JLabel("\u00A9Quadrinome n\u00B011 S2I");
		lblNewLabel.setBounds(10, 391, 121, 23);
		panel.add(lblNewLabel);
		aPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		profondeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
