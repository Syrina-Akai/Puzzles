package puzzleWindows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import puzzle.Taquin;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

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
		taquin.setBounds(144, 0, 243, 209);
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
		
		JLabel vide = new JLabel("");
		taquin.add(vide);
		
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
		
		String id = "123804765";
		JPanel taquin_1 = new JPanel();
		taquin_1.setBounds(429, 0, 243, 209);
		getContentPane().add(taquin_1);
		taquin_1.setLayout(new GridLayout(3, 3));
		
		// Id to an actual Taquin *******************************************************************************
		
		/*	Ma3reftech ndirha a function (ma3rftech win ndirha), well the function should take the id
			and treja3 a JPanel with all the components dakhelha or maybe treturni the components list
			But lazem the old panel (the one that contains the old taquin) lazem ndirolha remove l it's 
			components then n'ajoutiwelha ta3 hadi lfunction. */
		for (int i = 0; i < id.length(); i++) {
			if (id.charAt(i) == '0') {
				JLabel vide_1 = new JLabel("");
				taquin_1.add(vide_1);
			}
			else {
				JButton cell = new JButton(String.valueOf(id.charAt(i)));
				cell.setBackground(new Color(179, 229, 252));
				taquin_1.add(cell);
			}
		}
		// ******************************************************************************************************

		
		// Actual Taquin to an id  ******************************************************************************
		
		/*	This function should take the components list of the panel and return the id */
		StringBuilder idBuilder = new StringBuilder();
		for (Component component : taquin_1.getComponents()) {
			String componentClass = component.getClass().getName();
			/*	I checked with the last letter cuz i know that kayen ghir JButton 'n' and JLabel 'l'
			 	idk if there's a better way to do it but i think it's better than to check m3a
			 	"javax.swing.JButton" and "javax.swing.JLabel". */
			if (componentClass.charAt(componentClass.length()-1) == 'n') {
				JButton cell = (JButton) component;
				idBuilder.append(cell.getText());
			}
			else {
				JLabel vide_1 = (JLabel) component;
				idBuilder.append("0");
			}
		}
		System.out.println(idBuilder.toString());
		// ******************************************************************************************************
		
		
		aPropos.addActionListener(new ActionListener() {
			/*
			 * A propos rahi la3betha randomize
			 */
			public void actionPerformed(ActionEvent e) {
				Taquin t = new Taquin(true);
				System.out.println(t.id);
				taquin_1.removeAll();
				for (int i = 0; i < t.id.length(); i++) {
					if (t.id.charAt(i) == '0') {
						JLabel vide_1 = new JLabel("");
						taquin_1.add(vide_1);
					}
					else {
						JButton cell = new JButton(String.valueOf(t.id.charAt(i)));
						cell.setBackground(new Color(179, 229, 252));
						taquin_1.add(cell);
					}
				}
				taquin_1.revalidate();
			}
		});
		
		profondeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
