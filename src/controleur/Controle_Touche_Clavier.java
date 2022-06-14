package controleur;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.Plateau_De_Jeu;
import affichage_jeu.Image_Plateau;


public class Controle_Touche_Clavier implements KeyListener, ActionListener {
    Plateau_De_Jeu plateau;
    Image_Plateau view;
    // Test
    private int count = 0;
    private int niv = 1;


    public Controle_Touche_Clavier(Plateau_De_Jeu plateau, Image_Plateau view){
        this.plateau = plateau;
        this.view = view;
    }

    // Methode keytyped pour récupérer les déplacements éfféctués au clavier
    @Override
    public void keyTyped(KeyEvent e){
        if(this.plateau.isFinished() == false){
            switch(e.getKeyChar()){
                case 'd':
                    plateau.deplacement("droit");
                    plateau.afficheTableau();
                    System.out.println(" ");
                    break;
                case 'q':
                    plateau.deplacement("gauche");
                    plateau.afficheTableau();
                    System.out.println(" ");
                    break;
                case 'z':
                    plateau.deplacement("haut");
                    plateau.afficheTableau();
                    System.out.println(" ");
                    break;
                case 's':
                    plateau.deplacement("bas");
                    plateau.afficheTableau();
                    System.out.println(" ");
                    break;
            }
        }
        // Si le niveau est fini on affiche bouton Next Level et on appel Méthode suivant
        if(this.plateau.isFinished() == true){
            view.getNextLevel().setVisible(true);
            view.getNextLevel().addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    plateau.setScore(0);
                    // Test
                    count = count + 1;
                    System.out.println("Nombre d'appel controle touche : " + count);
                    try{
                        niv = niv + 1;
                        plateau.suivant(niv);
                    }
                    catch (Exception p){    // Si niveau pas trouvé on sort du jeu
                        System.exit(0);
                    }
                    view.getNextLevel().setVisible(false);
                    view.requestFocus();
                }
            });
        }
    }

    @Override
    public void keyPressed(KeyEvent e){

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
