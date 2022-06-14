package affichage_jeu;
import java.awt.Image;
import javax.swing.ImageIcon;
import game.Caisse;
import game.Position;

public class Image_Caisse extends Caisse{
    private Image caisse;
    private ImageIcon iconCaisse;

    // Constructeur Image
    public Image_Caisse(Position p){
        super(p);
        iconCaisse = new ImageIcon(getClass().getResource("images/box.png"));
        this.caisse = this.iconCaisse.getImage();
    }

    // Getteur
    public Image getCaisse(){
        return this.caisse;
    }
}