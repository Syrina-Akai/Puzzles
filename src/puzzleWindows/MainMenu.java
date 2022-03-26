package puzzleWindows;

import puzzle.Aetoile;
import puzzle.Largeur;
import puzzle.Profondeur;
import puzzle.Taquin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import static puzzle.Main.idBut;

public class MainMenu extends JFrame {
    public Stack<String> solution;
    public int children=0;
    public int nodes=0;
    String[] taquins;
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
    public MainMenu(String title) {
        super(title);
        String id;
        getContentPane().setBackground(new Color(237, 231, 246));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 880, 600);
        getContentPane().setLayout(null);
        JPanel taquin = new JPanel();
        taquin.setBounds(429, 73, 243, 209);
        getContentPane().add(taquin);
        taquin.setLayout(new GridLayout(3, 3));
        initTaquin(taquin, idBut);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(121, 134, 203));
        panel.setBounds(0, 0, 256, 563);
        getContentPane().add(panel);
        panel.setLayout(null);

        JRadioButton profondeurChex = new JRadioButton("Profondeur");
        profondeurChex.setForeground(Color.DARK_GRAY);
        profondeurChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        profondeurChex.setActionCommand("Profondeur");
        profondeurChex.setBackground(new Color(209, 196, 233));
        profondeurChex.setBounds(0, 230, 256, 47);
        panel.add(profondeurChex);

        JRadioButton largeurChex = new JRadioButton("Largeur");
        largeurChex.setForeground(Color.DARK_GRAY);
        largeurChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        largeurChex.setActionCommand("Largeur");
        largeurChex.setBackground(new Color(209, 196, 233));
        largeurChex.setBounds(0, 279, 256, 47);
        panel.add(largeurChex);

        JRadioButton manhattenChex = new JRadioButton("Manhattan");
        manhattenChex.setForeground(Color.DARK_GRAY);
        manhattenChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        manhattenChex.setActionCommand("Manhatten");
        manhattenChex.setBackground(new Color(209, 196, 233));
        manhattenChex.setBounds(0, 369, 256, 47);
        panel.add(manhattenChex);

        JRadioButton hammingChex = new JRadioButton("Hamming");
        hammingChex.setForeground(Color.DARK_GRAY);
        hammingChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hammingChex.setActionCommand("Hamming");
        hammingChex.setBackground(new Color(209, 196, 233));
        hammingChex.setBounds(0, 418, 256, 47);
        panel.add(hammingChex);

        ButtonGroup algos = new ButtonGroup();
        algos.add(profondeurChex);
        algos.add(largeurChex);
        algos.add(manhattenChex);
        algos.add(hammingChex);

        JLabel lblNewLabel_1 = new JLabel("Heuristique");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNewLabel_1.setBounds(10, 332, 121, 30);
        panel.add(lblNewLabel_1);

        idTaquin = new JTextField();
        idTaquin.setHorizontalAlignment(SwingConstants.CENTER);
        idTaquin.setText("Entrer le taquin");
        idTaquin.setBounds(10, 30, 236, 30);
        panel.add(idTaquin);
        idTaquin.setColumns(10);

        JLabel choixAlgo = new JLabel("Choisir l'algorithme de recherche");
        choixAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        choixAlgo.setForeground(Color.WHITE);
        choixAlgo.setBounds(16, 195, 240, 30);
        panel.add(choixAlgo);

        JButton convert = new JButton("Convertir");
        convert.setBounds(53, 70, 157, 30);
        convert.setBackground(new Color(197, 202, 233));
        panel.add(convert);

        JLabel taquinError = new JLabel("*Attention ce n'est pas un taquin");
        taquinError.setBounds(10, 10, 189, 13);
        panel.add(taquinError);
        taquinError.setForeground(Color.RED);

        JLabel ouSeparator = new JLabel("_____________OU BIEN_____________");
        ouSeparator.setBounds(21, 110, 249, 13);
        panel.add(ouSeparator);

        JButton randomTaquin = new JButton("Taquin al\u00E9atoire");
        randomTaquin.setBounds(53, 133, 157, 30);
        panel.add(randomTaquin);
        randomTaquin.setBackground(new Color(197, 202, 233));
        randomTaquin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        randomTaquin.setForeground(Color.BLACK);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 183, 256, 2);
        panel.add(separator);

        JButton solve = new JButton("Solve");
        solve.setBounds(36, 509, 175, 30);
        panel.add(solve);
        solve.setBackground(new Color(197, 202, 233));

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 492, 256, 2);
        panel.add(separator_1);

        JButton afficher = new JButton("Afficher solution");
        afficher.setBounds(462, 430, 175, 30);
        afficher.setBackground(new Color(197, 202, 233));
        getContentPane().add(afficher);

        randomTaquin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Taquin root = new Taquin(true);
                idTaquin.setText(root.id);
                initTaquin(taquin, root.id);
                taquinError.setVisible(false);
            }
        });
        taquinError.setVisible(false);

        JLabel lblNewLabel = new JLabel("\u00A9Quadrinome n\u00B011 S2I");
        lblNewLabel.setBounds(441, 540, 133, 23);
        getContentPane().add(lblNewLabel);

        JButton aPropos = new JButton("A propos");
        aPropos.setBounds(679, 532, 85, 21);
        getContentPane().add(aPropos);
        aPropos.setBackground(new Color(197, 202, 233));
        
        JPanel observation = new JPanel();
        observation.setBackground(new Color(121, 134, 203));
        observation.setBounds(310, 309, 505, 95);
        getContentPane().add(observation);
        observation.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("Le temps d'execution :");
        lblNewLabel_2.setBounds(10, 10, 218, 19);
        lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_2.setForeground(Color.WHITE);
        observation.add(lblNewLabel_2);
        
        JLabel executionTime = new JLabel("time..");
        executionTime.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        executionTime.setForeground(Color.WHITE);
        executionTime.setBounds(179, 13, 106, 13);
        observation.add(executionTime);
        
        JLabel lblNewLabel_3 = new JLabel("La taille du path :");
        lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_3.setForeground(Color.WHITE);
        lblNewLabel_3.setBounds(265, 10, 176, 19);
        observation.add(lblNewLabel_3);
        
        JLabel path = new JLabel("path..");
        path.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        path.setForeground(Color.WHITE);
        path.setBounds(413, 6, 45, 26);
        observation.add(path);
        
        JLabel lblNewLabel_4 = new JLabel("Le nombre des fils :");
        lblNewLabel_4.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_4.setForeground(Color.WHITE);
        lblNewLabel_4.setBounds(10, 53, 176, 13);
        observation.add(lblNewLabel_4);
        
        JLabel nbFils = new JLabel("fils...");
        nbFils.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nbFils.setForeground(Color.WHITE);
        nbFils.setBounds(171, 53, 57, 13);
        observation.add(nbFils);
        
        JLabel lblNewLabel_5 = new JLabel("Le nombre des noeuds :");
        lblNewLabel_5.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_5.setForeground(Color.WHITE);
        lblNewLabel_5.setBounds(265, 53, 185, 13);
        observation.add(lblNewLabel_5);
        
        JLabel nbNoeud = new JLabel("noeuds...");
        nbNoeud.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nbNoeud.setForeground(Color.WHITE);
        nbNoeud.setBounds(438, 53, 57, 13);
        observation.add(nbNoeud);
        observation.setVisible(false);
        aPropos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Quadrinome cool kids only !");
            }
        });

        idTaquin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //tester le text
                if (!idTaquin.getText().isEmpty() && testTextField(idTaquin.getText())) {
                    initTaquin(taquin, idTaquin.getText());
                    taquinError.setVisible(false);
                } else {
                    taquinError.setVisible(true);
                }
            }
        });

        idTaquin.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (idTaquin.getText().isEmpty()) {
                    taquinError.setVisible(false);
                    idTaquin.setText("Entrer le taquin");
                } else {
                    if (testTextField(idTaquin.getText())) {
                        initTaquin(taquin, idTaquin.getText());
                        taquinError.setVisible(false);
                    } else
                        taquinError.setVisible(true);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (idTaquin.getText().equals("Entrer le taquin")) {
                    idTaquin.setText("");
                    taquinError.setVisible(false);
                }
            }
        });

        convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //tester le text
                if (!idTaquin.getText().isEmpty() && testTextField(idTaquin.getText())) {
                    initTaquin(taquin, idTaquin.getText());
                    taquinError.setVisible(false);
                } else {
                    taquinError.setVisible(true);
                }
            }
        });

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("the al listener");
                if (!solution.isEmpty())
                    initTaquin(taquin, solution.pop());
                else {
                    System.out.println("stack empty");
                    Collections.addAll(solution,taquins);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.setRepeats(true);

        afficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(solution!=null)
                    timer.start();
            }
        });

        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!idTaquin.getText().equals("Choisir l'algorithme de recherche") || !idTaquin.getText().isEmpty()){
                    if (algos.getSelection() != null) {

                        choixAlgo.setText("Choisir l'algorithme de recherche");
                        choixAlgo.setForeground(Color.WHITE);
                        String algo = algos.getSelection().getActionCommand();
                        System.out.println("l'algo selectionne est : " + algo);
                        Taquin root = new Taquin(false);
                        root.idToTaquin(taquinToId(taquin));
                        int time=0;
                        switch (algo) {
                            case "Profondeur" -> {
                                Profondeur profondeur = new Profondeur();
                                LocalDateTime now = LocalDateTime.now();
                                profondeur.solve(root, 20);
                                LocalDateTime then = LocalDateTime.now();
                                time=then.getNano()-now.getNano();
                                solution=profondeur.getSolution();
                                taquins= solution.toArray(new String[0]);
                            }
                            case "Largeur" -> {
                                Largeur largeur = new Largeur();
                                LocalDateTime now = LocalDateTime.now();
                                largeur.solve(root);
                                LocalDateTime then = LocalDateTime.now();
                                time=then.getNano()-now.getNano();
                                solution = largeur.getSolution();
                                taquins= solution.toArray(new String[0]);
                            }
                            case "Manhatten" -> {
                                Aetoile aetoile = new Aetoile(1);
                                LocalDateTime now = LocalDateTime.now();
                                aetoile.solve(root);
                                LocalDateTime then = LocalDateTime.now();
                                time=then.getNano()-now.getNano();
                                solution = aetoile.getSolution();
                                taquins= solution.toArray(new String[0]);
                                children=aetoile.getFermer().size()+aetoile.getOuvert().size();
                                nodes=solution.size()+children+aetoile.getFils().size()*2;
                            }
                            case "Hamming" -> {
                                Aetoile aetoile = new Aetoile(2);
                                LocalDateTime now = LocalDateTime.now();
                                aetoile.solve(root);
                                LocalDateTime then = LocalDateTime.now();
                                time=then.getNano()-now.getNano();
                                solution = aetoile.getSolution();
                                taquins= solution.toArray(new String[0]);
                                children=aetoile.getFermer().size()+aetoile.getOuvert().size();
                                nodes=solution.size()+children+aetoile.getFils().size()*2;
                            }
                            default -> System.out.println("oups !");
                        }
                        if(solution.size()>1){
                            observation.setVisible(true);
                            path.setText(""+(solution.size()-2));
                            executionTime.setText(""+time+" ns");
                            nbFils.setText(""+children);
                            nbNoeud.setText(""+nodes);
                            System.out.println("children : "+children+" nodes : "+nodes);
                        }
                    } else {
                        choixAlgo.setText("*Choisir l'algorithme de recherche");
                        choixAlgo.setForeground(new Color(142, 0, 0));
                        System.out.println("faut choisir un algo");
                    }
                }
            }
        });

    }

    public void initTaquin(JPanel taquin, String id) {
        System.out.println(id);
        taquin.removeAll();
        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) == '0') {
                JLabel vide_1 = new JLabel("");
                taquin.add(vide_1);
            } else {
                JButton cell = new JButton(String.valueOf(id.charAt(i)));
                cell.setBackground(new Color(197, 202, 233));
                taquin.add(cell);
            }
        }
        taquin.revalidate();
    }

    public String taquinToId(JPanel taquin) {
        StringBuilder idBuilder = new StringBuilder();
        for (Component component : taquin.getComponents()) {
            String componentClass = component.getClass().getName();
            if (componentClass.charAt(componentClass.length() - 1) == 'n') {
                JButton cell = (JButton) component;
                idBuilder.append(cell.getText());
            } else {
                JLabel vide_1 = (JLabel) component;
                idBuilder.append("0");
            }
        }
        System.out.println(idBuilder);
        return idBuilder.toString();
    }

    public boolean testTextField(String text) {
        if (text.length() != 9) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            int count = text.length() - text.replace(String.valueOf(text.charAt(i)), "").length();
            if (count != 1 || !Character.isDigit(text.charAt(i)) || text.charAt(i) == '9') {
                return false;
            }
        }
        return true;
    }

    public boolean solvable(String id){
        int diff=0, i=0;
        while ( i < 9) {
            if(idBut.charAt(i) != id.charAt(i)){
                if(id.charAt(i+1)==idBut.charAt(i) && id.charAt(i)==idBut.charAt(i+1)) {//horizontale
                    diff++;
                }else{
                    if(id.charAt(i+3)==idBut.charAt(i) && id.charAt(i)==idBut.charAt(i+3)){//verticale
                        diff++;
                    }
                }
            }
            i++;
        }
        if(diff==2 || diff==4){
            return false;
        }
        return true;
    }
}
