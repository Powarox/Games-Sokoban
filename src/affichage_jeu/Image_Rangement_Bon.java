package affichage_jeu;
import java.awt.Image;
import javax.swing.ImageIcon;
import game.Position;

public class Image_Rangement_Bon extends Image_Caisse {
    private Image caisse;
    private ImageIcon iconCaisse;

    // Constructeur Image
    public Image_Rangement_Bon(Position p){
        super(p);
        iconCaisse = new ImageIcon(getClass().getResource("images/boxOk.png"));
        this.caisse = this.iconCaisse.getImage();
    }

    // Getteur
    public Image getCaisse(){
        return caisse;
    }
}