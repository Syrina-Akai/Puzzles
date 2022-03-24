package puzzleWindows;

import puzzle.Aetoile;
import puzzle.Largeur;
import puzzle.Taquin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Stack;

import static puzzle.Main.idBut;

public class MainMenu extends JFrame {
    public Stack<String> solution;
    private JTextField idTaquin;
    private boolean isSleep = true;

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
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(null);
        JPanel taquin = new JPanel();
        taquin.setBounds(388, 111, 243, 209);
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
        profondeurChex.setBackground(new Color(197, 202, 233));
        profondeurChex.setBounds(0, 230, 256, 47);
        panel.add(profondeurChex);

        JRadioButton largeurChex = new JRadioButton("Largeur");
        largeurChex.setForeground(Color.DARK_GRAY);
        largeurChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        largeurChex.setActionCommand("Largeur");
        largeurChex.setBackground(new Color(197, 202, 233));
        largeurChex.setBounds(0, 279, 256, 47);
        panel.add(largeurChex);

        JRadioButton manhattenChex = new JRadioButton("Manhatten");
        manhattenChex.setForeground(Color.DARK_GRAY);
        manhattenChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        manhattenChex.setActionCommand("Manhatten");
        manhattenChex.setBackground(new Color(197, 202, 233));
        manhattenChex.setBounds(0, 369, 256, 47);
        panel.add(manhattenChex);

        JRadioButton hammingChex = new JRadioButton("Hamming");
        hammingChex.setForeground(Color.DARK_GRAY);
        hammingChex.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hammingChex.setActionCommand("Hamming");
        hammingChex.setBackground(new Color(197, 202, 233));
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
        afficher.setBounds(421, 373, 175, 30);
        afficher.setBackground(new Color(197, 202, 233));
        getContentPane().add(afficher);

        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (algos.getSelection() != null) {
                    choixAlgo.setText("Choisir l'algorithme de recherche");
                    choixAlgo.setForeground(Color.black);
                    String algo = algos.getSelection().getActionCommand();
                    System.out.println("l'algo selectionne est : " + algo);
                    Taquin root = new Taquin(false);
                    root.idToTaquin(taquinToId(taquin));
                    Aetoile aetoile = new Aetoile();
                    switch (algo) {
                        case "Profondeur" -> {
									/*Profondeur profondeur = new Profondeur();
									profondeur.solve(root, 20);
									solution=profondeur.getSolution();*/
                        }
                        case "Largeur" -> {
                            Largeur largeur = new Largeur();
                            largeur.solve(root);
                            solution = largeur.getSolution();
                        }
                        case "Manhatten" -> {
                            aetoile.solve(root, 1);
                            solution = aetoile.getSolution();
                        }
                        case "Hamming" -> {
                            aetoile.solve(root, 2);
                            solution = aetoile.getSolution();
                        }
                        default -> System.out.println("oups !");
                    }
                } else {
                    choixAlgo.setText("*Choisir l'algorithme de recherche");
                    choixAlgo.setForeground(new Color(142, 0, 0));
                    System.out.println("faut choisir un algo");
                }
            }
        });

        randomTaquin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Taquin root = new Taquin(true);
                idTaquin.setText(root.id);
                initTaquin(taquin, root.id);
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
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.setRepeats(true);

        afficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
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
