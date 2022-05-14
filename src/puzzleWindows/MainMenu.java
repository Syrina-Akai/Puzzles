package puzzleWindows;

import puzzle.partie1.heuristic.Aetoile;
import puzzle.partie1.Largeur;
import puzzle.partie1.Profondeur;
import puzzle.Taquin;
import puzzle.partie1.heuristic.ManhattanComparator;
import puzzle.partie2.genitic.Chromosome;
import puzzle.partie2.genitic.GA;
import puzzle.partie2.genitic.SortChromosome;
import puzzle.partie2.pso.PSO;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Thread.sleep;
import static puzzle.Main.idBut;

public class MainMenu extends JFrame {
    public Stack<String> solution;
    public ArrayList<String> ouvert;
    public ArrayList<String> ferme;
    public int nodes = 0;
    String[] taquins;
    private JTextField idTaquin;
    int count=0;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    puzzleWindows.MainMenu frame = new puzzleWindows.MainMenu("Puzzle");
                    frame.setResizable(false);
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
        setBounds(100, 100, 943, 810);
        getContentPane().setLayout(null);
        JPanel taquin = new JPanel();
        taquin.setBounds(469, 87, 243, 209);
        getContentPane().add(taquin);
        taquin.setLayout(new GridLayout(3, 3));
        initTaquin(taquin, idBut);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(121, 134, 203));
        panel.setBounds(0, 0, 256, 853);
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
        manhattenChex.setBounds(0, 401, 256, 47);
        panel.add(manhattenChex);

        JRadioButton hammingChex = new JRadioButton("Hamming");
        hammingChex.setForeground(Color.DARK_GRAY);
        hammingChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hammingChex.setActionCommand("Hamming");
        hammingChex.setBackground(new Color(209, 196, 233));
        hammingChex.setBounds(0, 450, 256, 47);
        panel.add(hammingChex);

        JRadioButton rdbtnPso = new JRadioButton("PSO");
        rdbtnPso.setForeground(Color.DARK_GRAY);
        rdbtnPso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rdbtnPso.setBackground(new Color(209, 196, 233));
        rdbtnPso.setActionCommand("PSO");
        rdbtnPso.setBounds(0, 615, 256, 47);
        panel.add(rdbtnPso);

        JRadioButton rdbtnAlgorithmeGntique = new JRadioButton("Algorithme G\u00E9n\u00E9tique");
        rdbtnAlgorithmeGntique.setForeground(Color.DARK_GRAY);
        rdbtnAlgorithmeGntique.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rdbtnAlgorithmeGntique.setBackground(new Color(209, 196, 233));
        rdbtnAlgorithmeGntique.setActionCommand("GA");
        rdbtnAlgorithmeGntique.setBounds(0, 566, 256, 47);
        panel.add(rdbtnAlgorithmeGntique);

        ButtonGroup algos = new ButtonGroup();
        algos.add(profondeurChex);
        algos.add(largeurChex);
        algos.add(manhattenChex);
        algos.add(hammingChex);
        algos.add(rdbtnAlgorithmeGntique);
        algos.add(rdbtnPso);

        JLabel lblNewLabel_1 = new JLabel("Heuristiques");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNewLabel_1.setBounds(10, 365, 121, 30);
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
        solve.setBounds(53, 724, 157, 30);
        panel.add(solve);
        solve.setBackground(new Color(197, 202, 233));

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 530, 256, 2);
        panel.add(separator_1);

        JLabel profondeurLbl = new JLabel("Profondeur :");
        profondeurLbl.setBounds(10, 332, 121, 13);
        profondeurLbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        profondeurLbl.setForeground(Color.WHITE);
        profondeurLbl.setVisible(false);
        panel.add(profondeurLbl);

        JSpinner spinner = new JSpinner();
        spinner.setBounds(111, 332, 51, 23);
        spinner.setValue(10);
        spinner.setVisible(false);
        panel.add(spinner);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String algo=algos.getSelection().getActionCommand();
                if(algo.equals("Largeur") || algo.equals("Profondeur")){
                    spinner.setVisible(true);
                    profondeurLbl.setVisible(true);
                    return;
                }
                spinner.setVisible(false);
                profondeurLbl.setVisible(false);
            }
        };

        //set visible
        largeurChex.addActionListener(listener);
        profondeurChex.addActionListener(listener);
        hammingChex.addActionListener(listener);
        manhattenChex.addActionListener(listener);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setBounds(0, 364, 256, 2);
        panel.add(separator_1_1);

        JSeparator separator_1_2 = new JSeparator();
        separator_1_2.setBounds(0, 695, 256, 2);
        panel.add(separator_1_2);

        JLabel lblNewLabel_1_1 = new JLabel("Meta Heuristiques");
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNewLabel_1_1.setBounds(10, 530, 165, 30);
        panel.add(lblNewLabel_1_1);
        JButton afficher = new JButton("Afficher solution");
        afficher.setBounds(505, 402, 175, 30);
        afficher.setBackground(new Color(197, 202, 233));
        getContentPane().add(afficher);

        randomTaquin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Taquin root = new Taquin(true);
                idTaquin.setText(root.id);
                initTaquin(taquin, root.id);
                taquinError.setVisible(false);
                if(!root.isSolvable()){
                    JFrame alerte = new JFrame();
                    JOptionPane.showMessageDialog(alerte,"Taquin non solvable veuillez le changer.","Alert",JOptionPane.WARNING_MESSAGE);
                    solve.setEnabled(false);
                }else{
                    solve.setEnabled(true);
                }
            }
        });
        taquinError.setVisible(false);

        JLabel lblNewLabel = new JLabel("\u00A9Quadrinome n\u00B011 S2I");
        lblNewLabel.setBounds(388, 740, 133, 23);
        getContentPane().add(lblNewLabel);

        JButton aPropos = new JButton("A propos");
        aPropos.setBounds(739, 741, 85, 21);
        getContentPane().add(aPropos);
        aPropos.setBackground(new Color(197, 202, 233));

        JPanel observation = new JPanel();
        observation.setBackground(new Color(121, 134, 203));
        observation.setBounds(355, 530, 500, 100);
        getContentPane().add(observation);
        observation.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Le temps d'execution :");
        lblNewLabel_2.setBounds(122, 10, 218, 19);
        lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_2.setForeground(Color.WHITE);
        observation.add(lblNewLabel_2);

        JLabel executionTime = new JLabel("time..");
        executionTime.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        executionTime.setForeground(Color.WHITE);
        executionTime.setBounds(300, 10, 127, 19);
        observation.add(executionTime);

        JLabel lblNewLabel_3 = new JLabel("La taille du solution :");
        lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_3.setForeground(Color.WHITE);
        lblNewLabel_3.setBounds(272, 50, 176, 26);
        observation.add(lblNewLabel_3);

        JLabel path = new JLabel("path..");
        path.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        path.setForeground(Color.WHITE);
        path.setBounds(427, 50, 63, 26);
        observation.add(path);

        JLabel lblNewLabel_5 = new JLabel("Le nombre des noeuds :");
        lblNewLabel_5.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_5.setForeground(Color.WHITE);
        lblNewLabel_5.setBounds(10, 53, 185, 19);
        observation.add(lblNewLabel_5);

        JLabel nbNoeud = new JLabel("noeuds...");
        nbNoeud.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nbNoeud.setForeground(Color.WHITE);
        nbNoeud.setBounds(187, 53, 57, 19);
        observation.add(nbNoeud);
        observation.setVisible(false);

        JPanel ouvertPanel = new JPanel();
        ouvertPanel.setBounds(739, 225, 162, 249);
        getContentPane().add(ouvertPanel);
        ouvertPanel.setVisible(false);
        ouvertPanel.setBackground(new Color(237, 231, 246));
        ouvertPanel.setLayout(null);

        JLabel ouvertLb = new JLabel("Ouvert :");
        ouvertLb.setBounds(31, 10, 57, 21);
        ouvertPanel.add(ouvertLb);
        ouvertLb.setForeground(Color.BLACK);
        ouvertLb.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane ouvertPane = new JScrollPane();
        ouvertPane.setBounds(29, 41, 105, 162);
        ouvertPanel.add(ouvertPane);

        JLabel lblNewLabel_4_1 = new JLabel("Taille :");
        lblNewLabel_4_1.setForeground(Color.BLACK);
        lblNewLabel_4_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNewLabel_4_1.setBounds(43, 210, 45, 23);
        ouvertPanel.add(lblNewLabel_4_1);

        JLabel tailleO = new JLabel("tailleO");
        tailleO.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tailleO.setBounds(98, 211, 68, 20);
        ouvertPanel.add(tailleO);

        JPanel fermerPanel = new JPanel();
        fermerPanel.setLayout(null);
        fermerPanel.setBounds(278, 225, 162, 249);
        fermerPanel.setVisible(false);
        fermerPanel.setBackground(new Color(237, 231, 246));
        getContentPane().add(fermerPanel);

        JLabel lblNewLabel_6 = new JLabel("Ferm\u00E9 :");
        lblNewLabel_6.setBounds(28, 10, 57, 19);
        fermerPanel.add(lblNewLabel_6);
        lblNewLabel_6.setForeground(Color.BLACK);
        lblNewLabel_6.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane fermePane = new JScrollPane();
        fermePane.setBounds(28, 40, 105, 162);
        fermerPanel.add(fermePane);

        JLabel lblNewLabel_4 = new JLabel("Taille :");
        lblNewLabel_4.setForeground(Color.BLACK);
        lblNewLabel_4.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNewLabel_4.setBounds(38, 214, 45, 13);
        fermerPanel.add(lblNewLabel_4);

        JLabel tailleF = new JLabel("tailleF");
        tailleF.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tailleF.setBounds(93, 212, 68, 15);
        fermerPanel.add(tailleF);


        aPropos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Quadrinome cool kids only !");
                new APropos();
            }
        });

        idTaquin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //tester le text
                if (!idTaquin.getText().isEmpty() && testTextField(idTaquin.getText())) {
                    initTaquin(taquin, idTaquin.getText());
                    taquinError.setVisible(false);
                    Taquin root=new Taquin(false);
                    root.idToTaquin(idTaquin.getText());
                    if(!root.isSolvable()){
                        JFrame alerte = new JFrame();
                        JOptionPane.showMessageDialog(alerte,"Taquin non solvable veuillez le changer.","Alert",JOptionPane.WARNING_MESSAGE);
                        solve.setEnabled(false);
                    }else{
                        solve.setEnabled(true);
                    }
                } else {
                    taquinError.setVisible(true);
                    solve.setEnabled(false);
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
                        Taquin root=new Taquin(false);
                        root.idToTaquin(idTaquin.getText());
                        if(!root.isSolvable()){
                            JFrame alerte = new JFrame();
                            JOptionPane.showMessageDialog(alerte,"Taquin non solvable veuillez le changer.","Alert",JOptionPane.WARNING_MESSAGE);
                            solve.setEnabled(false);
                        }else{
                            solve.setEnabled(true);
                        }
                    } else {
                        taquinError.setVisible(true);
                        solve.setEnabled(false);
                    }
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
                    Taquin root=new Taquin(false);
                    root.idToTaquin(idTaquin.getText());
                    if(!root.isSolvable()){
                        JFrame alerte = new JFrame();
                        JOptionPane.showMessageDialog(alerte,"Taquin non solvable veuillez le changer.","Alert",JOptionPane.WARNING_MESSAGE);
                        solve.setEnabled(false);
                    }else{
                        solve.setEnabled(true);
                    }
                } else {
                    taquinError.setVisible(true);
                    solve.setEnabled(false);
                }
            }
        });

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!solution.isEmpty())
                    initTaquin(taquin, solution.pop());
                else {
                    System.out.println("stack empty");
                    Collections.addAll(solution, taquins);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.setRepeats(true);

        afficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solution != null)
                    timer.start();
            }
        });


        Timer timer1= new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!idTaquin.getText().equals("Choisir l'algorithme de recherche") || !idTaquin.getText().isEmpty()){
                    if (algos.getSelection() == null) {
                        choixAlgo.setText("*Choisir l'algorithme de recherche");
                        choixAlgo.setForeground(new Color(142, 0, 0));
                        System.out.println("faut choisir un algo");
                        ((Timer) e.getSource()).stop();
                    }else{
                        if(count==0){
                            System.out.println("COUNT 0");
                            choixAlgo.setText("Choisir l'algorithme de recherche");
                            choixAlgo.setForeground(Color.WHITE);
                            solve.setEnabled(false);
                            count++;
                            return;
                        }
                        if(count==1){
                            String algo = algos.getSelection().getActionCommand();
                            System.out.println("l'algo selectionne est : " + algo);
                            Taquin root = new Taquin(false);
                            root.idToTaquin(taquinToId(taquin));
                            ferme=new ArrayList<>();
                            ouvert=new ArrayList<>();
                            long timeElapsed = 0;
                            switch (algo) {
                                case "Profondeur" -> {
                                    Profondeur profondeur = new Profondeur();
                                    long startTime = System.nanoTime();
                                    profondeur.solve(root, (Integer) spinner.getValue());
                                    long endTime = System.nanoTime();
                                    timeElapsed = (endTime - startTime)/1000000;
                                    solution = profondeur.getSolution();
                                    taquins = solution.toArray(new String[0]);
                                    nodes=profondeur.getFerme().size();
                                    ouvertPanel.setVisible(false);
                                    ferme=profondeur.getFermeId();
                                }
                                case "Largeur" -> {
                                    Largeur largeur = new Largeur();
                                    long startTime = System.nanoTime();
                                    largeur.solve(root,(Integer)spinner.getValue());
                                    long endTime = System.nanoTime();
                                    timeElapsed = (endTime - startTime)/1000000;
                                    solution = largeur.getSolution();
                                    taquins = solution.toArray(new String[0]);
                                    nodes = largeur.getFermer().size() + largeur.getOuvert().size();
                                    ferme=largeur.getFermeId();
                                    ouvert=largeur.getOuvertId();
                                }
                                case "Manhatten" -> {
                                    Aetoile aetoile = new Aetoile(1);
                                    long startTime = System.nanoTime();
                                    aetoile.solve(root);
                                    long endTime = System.nanoTime();
                                    timeElapsed = (endTime - startTime)/1000000;
                                    solution = aetoile.getSolution();
                                    taquins = solution.toArray(new String[0]);
                                    nodes = aetoile.getFermer().size() + aetoile.getOuvert().size();
                                    ferme=aetoile.getFermeId();
                                    ouvert=aetoile.getOuvertId();
                                }
                                case "Hamming" -> {
                                    Aetoile aetoile = new Aetoile(2);
                                    long startTime = System.nanoTime();
                                    aetoile.solve(root);
                                    long endTime = System.nanoTime();
                                    timeElapsed = (endTime - startTime)/1000000;
                                    solution = aetoile.getSolution();
                                    taquins = solution.toArray(new String[0]);
                                    nodes = aetoile.getFermer().size() + aetoile.getOuvert().size();
                                    ferme=aetoile.getFermeId();
                                    ouvert=aetoile.getOuvertId();
                                }
                                case "GA" -> {
                                    GA ga = new GA(root);
                                    long startTime = System.nanoTime();
                                    ga.generateSolution();
                                    long endTime = System.nanoTime();
                                    timeElapsed = (endTime - startTime)/1000000;
                                    solution = ga.getSolution();
                                }
                                case "PSO" -> {
                                    System.out.println("PSO");
                                    PSO pso=new PSO(root.id);
                                    long startTime = System.nanoTime();
                                    pso.PSOmain();


                                }
                                default -> System.out.println("oups !");
                            }
                            if (solution.size() > 1) {
                                observation.setVisible(true);
                                path.setText("" + (solution.size() - 1));
                                executionTime.setText("" + timeElapsed + " ms");
                                nbNoeud.setText("" + nodes);
                                if(ferme.size()>1){
                                    tailleF.setText(""+ferme.size());
                                    fermerPanel.setVisible(true);
                                    System.out.println("l'ensemble affiché : "+ferme);
                                    JList<String> jferme = new JList<String>(ferme.toArray(new String[ferme.size()]));
                                    fermePane.setViewportView(jferme);
                                    jferme.setLayoutOrientation(JList.VERTICAL);
                                }else {
                                    fermerPanel.setVisible(false);
                                }

                                if(ouvert.size()>1){
                                    tailleO.setText(""+ouvert.size());
                                    ouvertPanel.setVisible(true);
                                    JList<String> jouvert = new JList<String>(ouvert.toArray(new String[ouvert.size()]));
                                    ouvertPane.setViewportView(jouvert);
                                    jouvert.setLayoutOrientation(JList.VERTICAL);
                                }else{
                                    ouvertPanel.setVisible(false);
                                }
                            }else{
                                observation.setVisible(false);
                                JFrame alerte = new JFrame();
                                JOptionPane.showMessageDialog(alerte,"Aucune solution trouvé.","Information",JOptionPane.INFORMATION_MESSAGE);
                            }
                            solve.setEnabled(true);
                            count=0;
                            ((Timer) e.getSource()).stop();
                        }
                    }
                }else{
                    count=0;
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer1.start();
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
}
