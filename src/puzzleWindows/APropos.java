package puzzleWindows;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Box;

public class APropos extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					APropos frame = new APropos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public APropos() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		setBounds(100, 100, 822, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(209, 196, 233));
		
		JLabel title = new JLabel("Jeu du Taquin");
		title.setFont(new Font("Segoe UI", Font.BOLD, 30));
		title.setBounds(303, 26, 211, 49);
		contentPane.add(title);
		
		JLabel lblNewLabel = new JLabel("Description");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel.setBounds(47, 111, 89, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html>Le jeu du taquin est un type de puzzle sous forme de matrice de taille carr\u00E9e.<br>L'application consiste \u00E0 r\u00E9soudre un taquin 3x3 en utilisant de diff\u00E9rents algorithmes.</html>");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(47, 125, 751, 66);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblRealisPar = new JLabel("Realis\u00E9 par");
		lblRealisPar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblRealisPar.setBounds(47, 211, 89, 25);
		contentPane.add(lblRealisPar);
		
		JLabel lblNewLabel_1_1 = new JLabel("<html>BENKHALDI Abdelaziz<br>FLICI Syrine Mahdia<br>TOLBA Yasmine<br>TOUMACHE Doudja Rania<br><br>Groupe 04</html>");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(47, 230, 196, 144);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("<html>Pr. DERIAS Habiba</html>");
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(555, 230, 144, 39);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblChargDeCour = new JLabel("Charg\u00E9 du Cours");
		lblChargDeCour.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblChargDeCour.setBounds(555, 211, 144, 25);
		contentPane.add(lblChargDeCour);
		
		JLabel lblChargDeTp = new JLabel("Charg\u00E9 du TP");
		lblChargDeTp.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblChargDeTp.setBounds(555, 279, 118, 25);
		contentPane.add(lblChargDeTp);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Dr. MOULAI Hadjer");
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(555, 300, 144, 39);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Version 1.0.0");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1_1.setBounds(357, 450, 89, 25);
		contentPane.add(lblNewLabel_1_1_1_1_1);
	}
}
