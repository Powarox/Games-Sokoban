package affichage_jeu;
import controleur.Controle_Touche_Clavier;
import game.Plateau_De_Jeu;
import java.awt.Color;
import javax.swing.JFrame;


public class Fenetre extends JFrame{
    Image_Plateau view;
    Plateau_De_Jeu plateau;
    Controle_Touche_Clavier ctr;

    // Constructeur Fenetre de jeu
    public Fenetre(Image_Plateau view, Plateau_De_Jeu plateau){
        this.plateau = plateau;
        this.view = view;
        ctr = new Controle_Touche_Clavier(plateau,view);
        this.setContentPane(view);
        this.setBackground(Color.WHITE);
        this.setTitle("SOKOBAN");
        this.setSize(805, 640);
        this.addKeyListener(ctr);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}