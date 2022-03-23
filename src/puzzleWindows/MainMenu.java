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

import static puzzle.Main.idBut;

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
		getContentPane().setBackground(new Color(237, 231, 246));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 462);
		getContentPane().setLayout(null);
		JPanel taquin = new JPanel();
		taquin.setBounds(180, 30, 243, 209);
		getContentPane().add(taquin);
		taquin.setLayout(new GridLayout(3, 3));
		initTaquin(taquin,idBut);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(121, 134, 203));
		panel.setBounds(0, 0, 153, 425);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton profondeur = new JButton("Profondeur");
		profondeur.setBounds(10, 33, 121, 21);
		profondeur.setBackground(new Color(197, 202, 233));
		panel.add(profondeur);
		
		JButton largeur = new JButton("Largeur");
		largeur.setBackground(new Color(197, 202, 233));
		largeur.setBounds(10, 79, 121, 21);
		panel.add(largeur);
		
		JComboBox Heuristique = new JComboBox();
		Heuristique.setBackground(new Color(197, 202, 233));
		Heuristique.setBounds(10, 126, 121, 21);
		panel.add(Heuristique);
		Heuristique.setModel(new DefaultComboBoxModel(new String[] {"Manhatten", "Hamming"}));
		
		JButton aPropos = new JButton("A propos");
		aPropos.setBackground(new Color(197, 202, 233));
		aPropos.setBounds(25, 360, 85, 21);
		panel.add(aPropos);
		
		JLabel lblNewLabel = new JLabel("\u00A9Quadrinome n\u00B011 S2I");
		lblNewLabel.setBounds(10, 391, 133, 23);
		panel.add(lblNewLabel);
		
		aPropos.addActionListener(new ActionListener() {
			/*
			 * A propos rahi la3betha randomize
			 */
			public void actionPerformed(ActionEvent e) {
				Taquin t = new Taquin(true);
				System.out.println(t.id);
				initTaquin(taquin,t.id);
			}
		});
		
		profondeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}

	public void initTaquin(JPanel taquin,String id){
		System.out.println(id);
		taquin.removeAll();
		for (int i = 0; i < id.length(); i++) {
			if (id.charAt(i) == '0') {
				JLabel vide_1 = new JLabel("");
				taquin.add(vide_1);
			}
			else {
				JButton cell = new JButton(String.valueOf(id.charAt(i)));
				cell.setBackground(new Color(197, 202, 233));
				taquin.add(cell);
			}
		}
		taquin.revalidate();
	}

	public String taquinToId(JPanel taquin){
		StringBuilder idBuilder = new StringBuilder();
		for (Component component : taquin.getComponents()) {
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
		return idBuilder.toString();
	}
}
