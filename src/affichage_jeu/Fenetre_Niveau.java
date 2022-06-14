package affichage_jeu;
import game.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;


public class Fenetre_Niveau extends JFrame implements KeyListener, ActionListener {
    JButton niveau1, niveau2, niveau3, retour;
    JPanel panelNiveau;

    // Constructeur Fenetre Niveau
    public Fenetre_Niveau(Fenetre fen){
        panelNiveau = this.Niveau(fen);
        this.setContentPane(panelNiveau);
        this.requestFocus();
        this.setVisible(true);
    }


    public JPanel Niveau(Fenetre fen){
        JPanel panel = new JPanel();
        this.setTitle("~ ~ S O K O B A N ~ ~ ");
        this.setSize(805, 640);
        panel.setLayout(null);
        JLabel title = new JLabel("Sokoban");
        title.setFont(new Font("Dialog Bold", Font.BOLD, 40));

        // Creation de bouton
        niveau1 = new JButton("   NIVEAU 1   ");
        niveau2 = new JButton("   NIVEAU 2   ");
        niveau3 = new JButton("   NIVEAU 3   ");
        retour = new JButton("   RETOUR MENU   ");

        // Creation d'une image de fond pour la Fenetre Graphique
        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/menu.png"));
        JButton button = new JButton(icon);
        button.setContentAreaFilled(false);
        button.setBounds(0,-17,805,640);

        // Changer la couleur des boutons!
        niveau1.setForeground(Color.WHITE);
        niveau1.setBackground(Color.BLACK);

        niveau2.setForeground(Color.WHITE);
        niveau2.setBackground(Color.BLACK);

        niveau3.setForeground(Color.WHITE);
        niveau3.setBackground(Color.BLACK);

        retour.setForeground(Color.WHITE);
        retour.setBackground(Color.BLACK);

        // Dimension des boutons
        niveau1.setBounds(350, 175, 150, 40);
        niveau2.setBounds(350, 275, 150, 40);
        niveau3.setBounds(350, 375, 150, 40);
        retour.setBounds(350, 475, 150, 40);

        // Event click : Lancer Niveau 1
        niveau1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué Niveau 1.");
                jeu(1);
                Fenetre_Niveau.this.setVisible(false);
                Fenetre_Niveau.this.setContentPane(panelNiveau);
                Fenetre_Niveau.this.revalidate();
            }
        });
        // Event click : Lancer Niveau 2
        niveau2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué Niveau 2.");
                jeu(2);
                Fenetre_Niveau.this.setVisible(false);
                Fenetre_Niveau.this.setContentPane(panelNiveau);
                Fenetre_Niveau.this.revalidate();
            }
        });
        // Event click : Lancer Niveau 3
        niveau3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué Niveau 3.");
                jeu(3);
                Fenetre_Niveau.this.setVisible(false);
                Fenetre_Niveau.this.setContentPane(panelNiveau);
                Fenetre_Niveau.this.revalidate();
            }
        });
        // Event click : Retour Fenetre Menu
        retour.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué sur Retour.");
                Fenetre_Niveau.this.setVisible(false);
                Fenetre_Niveau.this.setContentPane(panelNiveau);
                Fenetre_Niveau.this.revalidate();
                new Fenetre_Menu(fen);
            }
        });

        // afficher les boutons
        panel.add(niveau1);
        panel.add(niveau2);
        panel.add(niveau3);
        panel.add(retour);
        panel.add(button);

        return panel;
    }

    // Créer une nouvelle Fenetre de jeu avec le niveau que l'on souhaite lancer
    public void jeu(int niv){
        String chemin = "niveau" + niv + ".sok";
        ReadFile reader = new ReadFile(chemin);

        char matrice[][] = reader.matrice();
        Position p = reader.positionPersonnage(matrice);

        Player player = new Player(p);
        Plateau_De_Jeu plateau = new Plateau_De_Jeu(matrice, player);
        plateau.afficheTableau();

        Image_Plateau view = new Image_Plateau(plateau);
        Fenetre fen2 = new Fenetre(view, plateau);
        fen2.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){

    }

    @Override
    public void keyTyped(KeyEvent keyEvent){

    }

    @Override
    public void keyPressed(KeyEvent keyEvent){

    }

    @Override
    public void keyReleased(KeyEvent keyEvent){

    }
}