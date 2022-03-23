package puzzleWindows;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.*;

import puzzle.Aetoile;
import puzzle.Largeur;
import puzzle.Profondeur;
import puzzle.Taquin;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Stack;

import static puzzle.Main.idBut;

public class MainMenu extends JFrame {
	public Stack<String> solution;
	private JTextField idTaquin;
    private boolean isSleep=true;
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
		String id;
		getContentPane().setBackground(new Color(237, 231, 246));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 508);
		getContentPane().setLayout(null);
		JPanel taquin = new JPanel();
		taquin.setBounds(363, 27, 243, 209);
		getContentPane().add(taquin);
		taquin.setLayout(new GridLayout(3, 3));
		initTaquin(taquin,idBut);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(121, 134, 203));
		panel.setBounds(0, 0, 256, 471);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton aPropos = new JButton("A propos");
		aPropos.setBackground(new Color(197, 202, 233));
		aPropos.setBounds(68, 418, 85, 21);
		panel.add(aPropos);
		
		JLabel lblNewLabel = new JLabel("\u00A9Quadrinome n\u00B011 S2I");
		lblNewLabel.setBounds(53, 438, 133, 23);
		panel.add(lblNewLabel);
		
		JRadioButton profondeurChex = new JRadioButton("Profondeur");
		profondeurChex.setActionCommand("Profondeur");
		profondeurChex.setBackground(new Color(197, 202, 233));
		profondeurChex.setBounds(0, 196, 256, 21);
		panel.add(profondeurChex);
		
		JRadioButton largeurChex = new JRadioButton("Largeur");
		largeurChex.setActionCommand("Largeur");
		largeurChex.setBackground(new Color(197, 202, 233));
		largeurChex.setBounds(0, 219, 256, 21);
		panel.add(largeurChex);
		
		JRadioButton manhattenChex = new JRadioButton("Manhatten");
		manhattenChex.setActionCommand("Manhatten");
		manhattenChex.setBackground(new Color(197, 202, 233));
		manhattenChex.setBounds(0, 258, 256, 21);
		panel.add(manhattenChex);
		
		JRadioButton hammingChex = new JRadioButton("Hamming");
		hammingChex.setActionCommand("Hamming");
		hammingChex.setBackground(new Color(197, 202, 233));
		hammingChex.setBounds(0, 281, 256, 21);
		panel.add(hammingChex);

		ButtonGroup algos = new ButtonGroup();
		algos.add(profondeurChex);
		algos.add(largeurChex);
		algos.add(manhattenChex);
		algos.add(hammingChex);

		JLabel lblNewLabel_1 = new JLabel("Heuristique");
		lblNewLabel_1.setBounds(0, 243, 74, 13);
		panel.add(lblNewLabel_1);

		idTaquin = new JTextField();
		idTaquin.setText("Entrer le taquin");
		idTaquin.setBounds(10, 84, 128, 19);
		panel.add(idTaquin);
		idTaquin.setColumns(10);
		
		JLabel choixAlgo = new JLabel("Choisir l'algorithme de recherche");
		choixAlgo.setBounds(6, 161, 240, 13);
		panel.add(choixAlgo);
		
		JButton solve = new JButton("Solve");
		solve.setBounds(36, 349, 85, 21);
		solve.setBackground(new Color(197, 202, 233));
		panel.add(solve);
		
		JLabel taquinError = new JLabel("*Attention ce n'est pas un taquin");
		taquinError.setForeground(Color.RED);
		taquinError.setBounds(10, 61, 189, 13);
		panel.add(taquinError);
		
		JButton convert = new JButton("Convertir");
		convert.setBounds(148, 83, 98, 21);
		convert.setBackground(new Color(197, 202, 233));
		panel.add(convert);
		
		JButton randomTaquin = new JButton("Taquin al\u00E9atoire");
		randomTaquin.setBackground(new Color(197, 202, 233));
		randomTaquin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		randomTaquin.setForeground(Color.BLACK);
		randomTaquin.setBounds(10, 30, 128, 21);
		panel.add(randomTaquin);
		
		JButton afficher = new JButton("Afficher solution");
		afficher.setBounds(445, 265, 130, 21);
		afficher.setBackground(new Color(197, 202, 233));
		getContentPane().add(afficher);
		taquinError.setVisible(false);
		aPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Quadrinome cool kids only !");
			}
		});

		idTaquin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//tester le text
				if(!idTaquin.getText().isEmpty() && testTextField(idTaquin.getText())){
					initTaquin(taquin,idTaquin.getText());
					taquinError.setVisible(false);
				}else{
					taquinError.setVisible(true);
				}
			}
		});

		idTaquin.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(idTaquin.getText().isEmpty()) {
					taquinError.setVisible(false);
					idTaquin.setText("Entrer le taquin");
				}else {
					if(testTextField(idTaquin.getText())){
						initTaquin(taquin,idTaquin.getText());
						taquinError.setVisible(false);
					}else
						taquinError.setVisible(true);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if(idTaquin.getText().equals("Entrer le taquin")) {
					idTaquin.setText("");
					taquinError.setVisible(false);
				}
			}
		});

		convert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//tester le text
				if(!idTaquin.getText().isEmpty() && testTextField(idTaquin.getText())){
					initTaquin(taquin,idTaquin.getText());
					taquinError.setVisible(false);
				}else{
					taquinError.setVisible(true);
				}
			}
		});

		randomTaquin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Taquin root= new Taquin(true);
				idTaquin.setText(root.id);
				initTaquin(taquin,root.id);
			}
		});

		solve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(algos.getSelection()!=null){
					choixAlgo.setText("Choisir l'algorithme de recherche");
					choixAlgo.setForeground(Color.black);
					String algo =algos.getSelection().getActionCommand();
					System.out.println("l'algo selectionne est : "+algo);
					Taquin root= new Taquin(false);
					root.idToTaquin(taquinToId(taquin));
					Aetoile aetoile= new Aetoile();
					switch (algo) {
						case "Profondeur" -> {
							Profondeur profondeur = new Profondeur();
							profondeur.solve(root, 20);
							solution=profondeur.getSolution();
						}
						case "Largeur" -> {
							Largeur largeur = new Largeur();
							largeur.solve(root);
							solution=largeur.getSolution();
						}
						case "Manhatten" -> {
							aetoile.solve(root, 1);
							solution=aetoile.getSolution();
						}
						case "Hamming" -> {
							aetoile.solve(root, 2);
							solution=aetoile.getSolution();
						}
						default -> System.out.println("oups !");
					}
				}else{
					choixAlgo.setText("*Choisir l'algorithme de recherche");
					choixAlgo.setForeground(new Color(142, 0, 0));
					System.out.println("faut choisir un algo");
				}
			}
		});

		 Timer timer= new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("the al listener");
				if (!solution.isEmpty())
					initTaquin(taquin,solution.pop() );
				else
				if(solution.isEmpty()){
					//stop();
				}
			}
		});

		 afficher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
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
			if (componentClass.charAt(componentClass.length()-1) == 'n') {
				JButton cell = (JButton) component;
				idBuilder.append(cell.getText());
			}
			else {
				JLabel vide_1 = (JLabel) component;
				idBuilder.append("0");
			}
		}
		System.out.println(idBuilder);
		return idBuilder.toString();
	}

	public boolean testTextField(String text){
		if(text.length()!=9){
			return false;
		}
		for(int i =0; i<text.length();i++){
			int count = text.length() - text.replace(String.valueOf(text.charAt(i)), "").length();
			if(count!=1 || !Character.isDigit(text.charAt(i)) || text.charAt(i)=='9'){
				return false;
			}
		}
		return true;
	}

}
