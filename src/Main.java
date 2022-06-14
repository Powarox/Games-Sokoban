
import java.io.IOException;
import javax.swing.JFrame;
import affichage_jeu.Fenetre;
import affichage_jeu.Fenetre_Menu;
import affichage_jeu.Image_Plateau;
import controleur.Controle_Touche_Clavier;
import game.Plateau_De_Jeu;
import game.Player;
import game.Position;
import game.ReadFile;


public class Main extends JFrame{

    public static void main(String[] args) throws IOException {
        ReadFile reader;
        reader = new ReadFile("niveau1.sok");
        char matrice[][] = reader.matrice();

        Position p = reader.positionPersonnage(matrice);
        Player player = new Player(p);

        Plateau_De_Jeu plateau = new Plateau_De_Jeu(matrice, player);

        plateau.afficheTableau();

        Image_Plateau view = new Image_Plateau(plateau);

        Fenetre fen = new Fenetre(view, plateau);
        Fenetre_Menu f = new Fenetre_Menu(fen);

        Controle_Touche_Clavier ctr = new Controle_Touche_Clavier(plateau, view);
        view.addKeyListener(ctr);
    }
}