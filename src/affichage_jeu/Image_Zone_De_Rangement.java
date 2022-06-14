package affichage_jeu;
import java.awt.Image;
import javax.swing.ImageIcon;
import game.Position;
import game.Zone;

public class Image_Zone_De_Rangement extends Zone {
    private Image destination;
    private ImageIcon iconDestination;

    // Constructeur Image
    public Image_Zone_De_Rangement(Position position){
        super(position);
        iconDestination = new ImageIcon(getClass().getResource("images/destination.png"));
        this.destination = this.iconDestination.getImage();
    }

    // Getteur
    public Image getdestination(){
        return this.destination;
    }
}