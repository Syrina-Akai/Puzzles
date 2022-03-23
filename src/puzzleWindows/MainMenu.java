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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static puzzle.Main.idBut;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.DropMode;

public class MainMenu extends JFrame {
	private JTextField idTaquin;

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
		setBounds(100, 100, 739, 508);
		getContentPane().setLayout(null);
		JPanel taquin = new JPanel();
		taquin.setBounds(254, 29, 243, 209);
		getContentPane().add(taquin);
		taquin.setLayout(new GridLayout(3, 3));
		initTaquin(taquin,idBut);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(121, 134, 203));
		panel.setBounds(0, 0, 209, 471);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton aPropos = new JButton("A propos");
		aPropos.setBackground(new Color(197, 202, 233));
		aPropos.setBounds(25, 418, 85, 21);
		panel.add(aPropos);
		
		JLabel lblNewLabel = new JLabel("\u00A9Quadrinome n\u00B011 S2I");
		lblNewLabel.setBounds(10, 438, 133, 23);
		panel.add(lblNewLabel);
		
		JRadioButton profondeurChex = new JRadioButton("Profondeur");
		profondeurChex.setBackground(new Color(197, 202, 233));
		profondeurChex.setBounds(11, 196, 128, 21);
		panel.add(profondeurChex);
		
		JRadioButton largeurChex = new JRadioButton("Largeur");
		largeurChex.setBackground(new Color(197, 202, 233));
		largeurChex.setBounds(11, 229, 128, 21);
		panel.add(largeurChex);
		
		JRadioButton manhattenChex = new JRadioButton("Manhatten");
		manhattenChex.setBackground(new Color(197, 202, 233));
		manhattenChex.setBounds(15, 275, 124, 21);
		panel.add(manhattenChex);
		
		JRadioButton hammingChex = new JRadioButton("Hamming");
		hammingChex.setBackground(new Color(197, 202, 233));
		hammingChex.setBounds(15, 310, 124, 21);
		panel.add(hammingChex);
		
		JLabel lblNewLabel_1 = new JLabel("Heuristique");
		lblNewLabel_1.setBounds(15, 256, 74, 13);
		panel.add(lblNewLabel_1);

		JCheckBox randomChex = new JCheckBox("Taquin al\u00E9atoire");
		randomChex.setBounds(12, 49, 127, 21);
		panel.add(randomChex);

		idTaquin = new JTextField();
		idTaquin.setText("Entrer le taquin");
		idTaquin.setBounds(11, 76, 128, 19);
		panel.add(idTaquin);
		idTaquin.setColumns(10);
		idTaquin.setEnabled(!randomChex.isSelected());
		
		JLabel lblNewLabel_2 = new JLabel("Choisir l'algorithme de recherche ");
		lblNewLabel_2.setBounds(6, 161, 193, 13);
		panel.add(lblNewLabel_2);
		
		JButton solve = new JButton("Solve");
		solve.setBounds(36, 349, 85, 21);
		solve.setBackground(new Color(197, 202, 233));
		panel.add(solve);

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

		idTaquin.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if(idTaquin.getText().isEmpty()) {
					idTaquin.setText("Entrer le taquin");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if(idTaquin.getText().equals("Entrer le taquin")) {
					idTaquin.setText("");
				}
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
