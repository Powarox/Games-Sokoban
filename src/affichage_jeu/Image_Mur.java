package affichage_jeu;
import java.awt.Image;
import javax.swing.ImageIcon;
import game.Mur;
import game.Position;

public class Image_Mur extends Mur {
    private Image mur;
    private ImageIcon iconMur;

    // Constructeur Image
    public Image_Mur(Position p){
        super(p);
        iconMur = new ImageIcon(getClass().getResource("images/mur.png"));
        this.mur = this.iconMur.getImage();
    }

    // Getteur
    public Image getMur(){
        return this.mur;
    }
}