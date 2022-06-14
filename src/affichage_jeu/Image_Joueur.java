package affichage_jeu;
import java.awt.Image;
import javax.swing.ImageIcon;
import game.Player;
import game.Position;

public class Image_Joueur extends Player{
    private Image personnage;
    private ImageIcon iconPersonnage;

    // Constructeur Image
    public Image_Joueur (Position position) {
        super(position);
        iconPersonnage = new ImageIcon(getClass().getResource("images/personnage.png"));
        this.personnage = this.iconPersonnage.getImage();
    }

    // Getteur
    public Image getPersonnage(){
        return this.personnage;
    }
}