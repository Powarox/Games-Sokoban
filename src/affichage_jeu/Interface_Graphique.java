package affichage_jeu;
import affichage_jeu.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import javax.imageio.ImageIO;


public class Interface_Graphique extends JFrame {
	private static final long serialVersionUID = 1L;
	private Sokoban solver;
	private JButton submit, suivant, prev;
	private JTextArea answerText;
	private JLabel loadingLabel, stepLabel;
	private JComboBox searchMenu, heuristicsMenu;
	private char hChoice = ' ';
	private JPanel questionPanel;

	private JLabel questionLabel1 = new JLabel();
	private JLabel questionLabel2 = new JLabel();
	private JTextField questionField1 = new JTextField();
	
	private int numRow, numCol, currentStep;
	private String questionSelected = " ";
	private String solution = "";
	private String[] steps;
	
	private HashSet<Coordonnes> walls;
	private HashSet<Coordonnes> goals;
	private HashSet<Coordonnes> boxes;
	private Coordonnes player;
	private String[] puzzleStates; // string containing puzzle state for each step
	
	// choix pour menu
	private String[] Choix = {"Greedy", "A*"};
	private String[] choix2 = {"Manhattan", "Euclidean",};
	
	// Constructeur Fenetre ia
	public Interface_Graphique() throws IOException {
		init();
		updateQuestion("default");
		setSize(700, 500);
		setTitle("Sokoban Solver");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		solver = new Sokoban();
	}

	// Initialisation Interface_Graphique et Panel
	private void init() throws IOException {
		JPanel mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(getTopPanel(), BorderLayout.NORTH); // Panel pour question et logo
		mainPanel.add(getAnswerPanel(), BorderLayout.CENTER); // Reponse panel
		mainPanel.add(getLoadingPanel(), BorderLayout.SOUTH); // Chargement panel
		addListeners();
	}
	

	private JPanel getLoadingPanel() {
		JPanel loadingPanel = new JPanel();
		loadingPanel.setBackground(new Color(45,45,45));
		SpringLayout layout = new SpringLayout();
		loadingPanel.setLayout(layout);
		loadingPanel.setPreferredSize(new Dimension(700,25));

		loadingLabel = new JLabel();
		loadingLabel.setText(" ");

		stepLabel = new JLabel();
		stepLabel.setText(" ");
		stepLabel.setVisible(false);

		prev = new JButton("Suivant");
		prev.setVisible(false);
		suivant = new JButton("Precedent");
		suivant.setVisible(false);
		
		loadingPanel.add(loadingLabel);
		loadingPanel.add(stepLabel);
		loadingPanel.add(prev);
		loadingPanel.add(suivant);

		layout.putConstraint(SpringLayout.WEST, loadingLabel,
                10, SpringLayout.WEST, loadingPanel);
		layout.putConstraint(SpringLayout.NORTH, loadingLabel,
                15, SpringLayout.NORTH, loadingPanel);
		layout.putConstraint(SpringLayout.EAST, suivant, -10,
				SpringLayout.EAST, loadingPanel);
		layout.putConstraint(SpringLayout.NORTH, suivant, 10,
				SpringLayout.NORTH, loadingPanel);
		layout.putConstraint(SpringLayout.EAST, prev, 10,
				SpringLayout.WEST, suivant);
		layout.putConstraint(SpringLayout.NORTH, prev, 10,
				SpringLayout.NORTH, loadingPanel);
		layout.putConstraint(SpringLayout.EAST, stepLabel, 0, 
				SpringLayout.WEST, prev);
		layout.putConstraint(SpringLayout.NORTH, stepLabel, 15, 
				SpringLayout.NORTH, loadingPanel);
		
		return loadingPanel;
	}


	private JScrollPane getAnswerPanel() {
		JPanel answerPanel = new JPanel();

		answerPanel.setLayout(new BorderLayout());
		answerText = new JTextArea();
		answerText.setText("");
		answerText.setSize(new Dimension(650, 100));
		answerText.setEditable(false);
		answerText.setLineWrap(true);
		Font font = new Font("Monaco", Font.PLAIN, 12);
        answerText.setFont(font);
		
		answerPanel.add(answerText);
		JScrollPane answerPane = new JScrollPane(answerPanel);

		answerPane.setSize(new Dimension(700,350));
		answerPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

		return answerPane;
	}


	private JPanel getTopPanel() throws IOException {
		JPanel topPanel = new JPanel();
		topPanel.setSize(new Dimension(700, 100));
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.setBackground(new Color(45,45,45));
		
		// Logo SOKOBAN
		Image logo = ImageIO.read(new File("src/affichage_jeu/images/image_intro.png"));
		Image resizedLogo = logo.getScaledInstance(700, 50, Image.SCALE_SMOOTH); // resize image to fit the frame
		JLabel picLabel = new JLabel(new ImageIcon(resizedLogo));
		picLabel.setSize(700,50);
		topPanel.add(picLabel);
		
		// add questionPanel on the bottom grid
		questionPanel = new JPanel();
		questionPanel.setLayout(new BorderLayout());
		questionPanel.setSize(700, 50);
		questionLabel1.setBackground(new Color(45,45,45));
		questionLabel1.setForeground(new Color(255,255,255));
		
		// add drop-down menu for questions into questionPanel
		searchMenu = new JComboBox(Choix);
		searchMenu.setSize(100, 40);
		searchMenu.setEditable(false);
		searchMenu.setBackground(new Color(45,45,45));
		searchMenu.setForeground(new Color(255,255,255));

		heuristicsMenu = new JComboBox(choix2);
		heuristicsMenu.setSize(80, 40);
		heuristicsMenu.setEditable(false);
		heuristicsMenu.setVisible(false);
		heuristicsMenu.setBackground(new Color(45,45,45));
		heuristicsMenu.setForeground(new Color(255,255,255));
		
		// add submit button to the questionPanel
		submit = new JButton("Resoudre");
		submit.setBackground(new Color(45,45,45));
		submit.setForeground(new Color(255,255,255));

		// add question labels and fields to questionPanel
		SpringLayout layout = new SpringLayout();
		JPanel labelPanel = new JPanel(layout);
		labelPanel.add(questionLabel1);
		labelPanel.add(questionField1);
		labelPanel.add(questionLabel2);

		labelPanel.add(heuristicsMenu);
		setLayoutBounds(layout, labelPanel); // set bounds between each labels
		
		questionPanel.add(searchMenu, BorderLayout.WEST);	
		questionPanel.add(labelPanel, BorderLayout.CENTER);
		questionPanel.add(submit, BorderLayout.EAST);
		topPanel.add(questionPanel);
		return topPanel;
	}
	

// Action evenement
	private void addListeners() {
		// ajoute actionListener a search menu
		searchMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				questionSelected = comboBox.getSelectedItem().toString();
				updateQuestion(questionSelected.toLowerCase());
			}
		});

		// ajoute actionListener a heuristics menu
		heuristicsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				String selected = comboBox.getSelectedItem().toString();
				if (selected.contains("Max")) {
					hChoice = 'x';
				}
				else
					hChoice = selected.charAt(0);
			}
		});

		submit.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
			}

			// display loading message when mouse is pressed
			public void mousePressed(MouseEvent arg0) {
				if (submit.isEnabled()) {
					stepLabel.setVisible(false);
					prev.setVisible(false);
					suivant.setVisible(false);

				}
			}
		});
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// affiche la reponse dans le panel du mileu
				try {
					int numPlayer = solver.loadFile(questionField1.getText(), hChoice);
					if (numPlayer != 1) {

					}
					else {
						numRow = solver.getligne();
						numCol = solver.getColonne();
						goals = solver.getGoals();
						walls = solver.getWalls();
						boxes = solver.getBoxes();
						player = solver.getPlayer();
						currentStep = 0;
						char method = Character.toLowerCase(questionSelected.charAt(0));
						String answer = solver.solve(method);
						System.out.println(answer);
						String[] lines = answer.split("\\r?\\n");
						solution = lines[1];
						steps = solution.split(" ");
						puzzleStates = new String[steps.length+1];

						if (answer.contains("Failed")) {

							repaint();
						}
						else {

							stepLabel.setVisible(true);
							prev.setVisible(true);
							suivant.setVisible(true);
							updatePuzzle();
						}
					}
				} catch (NumberFormatException e) {

				} catch (FileNotFoundException e) {

				} catch (NoSuchElementException e) {

				}
			}
		});	
		
		// add actionListener au boutton precedent
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentStep>0) {
					currentStep -= 1;
					updatePuzzle();
				}
			}
		});
		
		// ajoute actionListener au boutton suivant
		suivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// check if out of bounds
				if (currentStep<steps.length) {
					// check if puzzle state has been seen
					if (puzzleStates[currentStep+1]==null) {
						int row = player.ligne;
						int col = player.colonne;
						// update player and box (if needed) positions
						if (steps[currentStep].equals("u")) {
							Coordonnes checkBox = new Coordonnes(row-1, col);
							if (boxes.contains(checkBox)) {
								boxes.remove(checkBox);
								boxes.add(new Coordonnes(row-2, col));
							}
							player = new Coordonnes(row-1, col);
						}
						else if (steps[currentStep].equals("d")) {
							Coordonnes checkBox = new Coordonnes(row+1, col);
							if (boxes.contains(checkBox)) {
								boxes.remove(checkBox);
								boxes.add(new Coordonnes(row+2, col));
							}
							player = new Coordonnes(row+1, col);
						}
						else if (steps[currentStep].equals("l")) {
							Coordonnes checkBox = new Coordonnes(row, col-1);
							if (boxes.contains(checkBox)) {
								boxes.remove(checkBox);
								boxes.add(new Coordonnes(row, col-2));
							}
							player = new Coordonnes(row, col-1);
						}
						else if (steps[currentStep].equals("r")) {
							Coordonnes checkBox = new Coordonnes(row, col+1);
							if (boxes.contains(checkBox)) {
								boxes.remove(checkBox);
								boxes.add(new Coordonnes(row, col+2));
							}
							player = new Coordonnes(row, col+1);
						}
					}
					currentStep += 1;
					updatePuzzle();
				}
			}
		});
	
	}


	 // Updates le jeu suivant le player et les boxes

	private void updatePuzzle() {
		int totalSteps = steps.length;
		String output = "La solution est : " + solution + " ~ ~ Nombres de coups est  " + totalSteps +"\n\n";
		output += "Etape " + currentStep + ":\n";
		if (puzzleStates[currentStep] == null) {
			String position = "";
			for (int i=0; i<numRow; i++) {
				for (int j=0; j<numCol; j++) {
					Coordonnes c = new Coordonnes(i, j);
					if (player.equals(c))
						position += "@";
					else if (boxes.contains(c))
						position += "$";
					else if (goals.contains(c))
						position += ".";
					else if (walls.contains(c))
						position += "#";
					else
						position += " ";
				}
				position += "\n";
			}
			output += position;
			puzzleStates[currentStep] = position;
		}
		else
			output += puzzleStates[currentStep];
		answerText.setText(output);
		repaint();
	}

	// Posotionne les Jpanel
	private void setLayoutBounds(SpringLayout layout, JPanel labelPanel) {
		layout.putConstraint(SpringLayout.WEST, questionLabel1,
                5, SpringLayout.WEST, labelPanel);
		layout.putConstraint(SpringLayout.NORTH, questionLabel1,
                17, SpringLayout.NORTH, labelPanel);
		layout.putConstraint(SpringLayout.WEST, questionField1,
                1, SpringLayout.EAST, questionLabel1);
		layout.putConstraint(SpringLayout.NORTH, questionField1,
                15, SpringLayout.NORTH, labelPanel);
		layout.putConstraint(SpringLayout.WEST, questionLabel2,
                1, SpringLayout.EAST, questionField1);
		layout.putConstraint(SpringLayout.NORTH, questionLabel2,
                17, SpringLayout.NORTH, labelPanel);
		layout.putConstraint(SpringLayout.WEST, heuristicsMenu,
                1, SpringLayout.EAST, questionLabel2);
		layout.putConstraint(SpringLayout.NORTH, heuristicsMenu,
                12, SpringLayout.NORTH, labelPanel);
	}
	

	// Questionnaire
	private void updateQuestion(String selected) {
		questionLabel1.setText("Entrer le nom du fichier : ");
		questionField1.setText("Nom Du Fichier");
		questionField1.setPreferredSize(new Dimension(105, 20));
		questionField1.setVisible(true);
		submit.setEnabled(true);
		if (selected.equals("a*")||selected.equals("greedy")) {
			questionLabel2.setText(". Heuristics: ");
			heuristicsMenu.setVisible(true);
		}

		repaint();
	}
}