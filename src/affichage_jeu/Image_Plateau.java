package affichage_jeu;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import game.Plateau_De_Jeu;
import game.Player;
import game.Position;
import game.ReadFile;


public class Image_Plateau extends JPanel implements Observer, ActionListener {
    // Constante d'espacement des objet
    private int indiceX;
    private int indiceY;
    private int espace = 45;

    // variables image du jeu
    private Image_Mur mur;
    private Image_Caisse caisse;
    private Image_Joueur personnage;
    private Image_Joueur personnage2;
    private Image_Zone_De_Rangement zoneRangement;
    private Image_Rangement_Bon caisseSurZone;
    private Plateau_De_Jeu plateau;

    // Barre de la fenêtre et composants
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu1 = new JMenu();
    private JMenuItem niveau = new JMenuItem();
    private JMenuItem relancer = new JMenuItem();
    private JMenuItem relancerMenu = new JMenuItem();
    private JPanel footer = new JPanel();
    private JLabel label = new JLabel("Score : " + 0);
    private JButton nextLevel = new JButton("Next Level");

     // Liste des position des objets
    private ArrayList<Image_Mur> listeMur;
    private ArrayList<Image_Caisse> listeCaisse;
    private ArrayList<Image_Zone_De_Rangement> listeDestination;
    private ArrayList<Image_Rangement_Bon> listeCaisseSurZone;

    // Getters et Setters
    public JMenuItem getRelancer(){
        return this.relancer;
    }
    public JMenuItem getRelancerMenu(){
        return this.relancerMenu;
    }
    public JMenuItem getNiveau(){
        return this.niveau;
    }
    public JButton getNextLevel(){
        return this.nextLevel;
    }

    // Constructeur
    public Image_Plateau(Plateau_De_Jeu plateau){
        this.plateau = plateau;
        this.plateau.addObserver(this);
        footer.setBackground(Color.BLACK);
        menuBar.setBackground(Color.BLACK);
        nextLevel.setVisible(false);
        menu1.setForeground(new Color(225,225,225));
        label.setForeground(new Color(255,255,255));
        menu1.setText("Options");
        niveau.setText("Importer niveaux");
        relancer.setText("Relancer");
        relancerMenu.setText("Relancer Menu");
        // Ajoute les Boutons
        menu1.add(niveau);
        menu1.add(relancer);
        menu1.add(relancerMenu);
        menuBar.add(menu1);
        footer.add(label);
        footer.add(nextLevel);
        this.setLayout(new BorderLayout());
        this.add(menuBar , BorderLayout.NORTH);
        this.add(footer , BorderLayout.SOUTH);
        // Bouton qui permet de passer au niveau suivant
        footer.add(nextLevel);
        // Ajout des Event aux boutons
        relancer.addActionListener(this);
        relancerMenu.addActionListener(this);
        niveau.addActionListener(this);
    }

    public void actionPerformed(ActionEvent arg0){
        // Relance le Niveau
        if(arg0.getSource() == getRelancer()){
            plateau.relancerJeu();
            plateau.setScore(0);
        }
        if(arg0.getSource() == getNiveau()){
            File f = plateau.pickMe();
            if(f != null){
                ReadFile r = new ReadFile(f);
                char[][] mat = r.matrice();
                plateau.setPlateau(mat);
                plateau.setPlayer(new Player(r.positionPersonnage(mat)));
                plateau.deplacement("");
            }
        }
        // Relance la Fenetre Menu
        if(arg0.getSource() == getRelancerMenu()){
            plateau.relancerMenu();
        }
    }


    // Associe chaques caractères avec son image correspondante
    public void init(){
        indiceX = 100;
        indiceY = 100;
        listeMur = new ArrayList<>();
        listeCaisse = new ArrayList<>();
        listeDestination = new ArrayList<>();
        listeCaisseSurZone = new ArrayList<>();
        for(int i = 0; i < plateau.getPlateau().length; i++){
            for(int j = 0; j < plateau.getPlateau().length; j++){
                switch(plateau.getPlateau()[i][j]){
                    case '#':{
                        //on a un mur
                        mur = new Image_Mur(new Position(indiceX, indiceY));
                        listeMur.add(mur);
                        indiceX = indiceX + espace;
                    }
                    break;
                    case '$':{
                        //Caisse
                        caisse = new Image_Caisse(new Position(indiceX, indiceY));
                        listeCaisse.add(caisse);
                        indiceX = indiceX + espace;
                    }
                    break;
                    case '@':{
                        // Joueur
                        personnage = new Image_Joueur(new Position(indiceX, indiceY));
                        personnage2 = new Image_Joueur(new Position(indiceX, indiceY));
                        indiceX = indiceX + espace;
                    }
                    break;
                    case '.':{
                        //zone rangement
                        zoneRangement = new Image_Zone_De_Rangement(new Position(indiceX, indiceY));
                        listeDestination.add(zoneRangement);
                        indiceX = indiceX + espace;
                    }
                    break;
                    case '+': {
                        //personnage sur zone de rangement
                        personnage = new Image_Joueur( new Position( indiceX, indiceY ) );
                        indiceX = indiceX + espace;
                    }
                    break;
                    case '*': {
                        //caisse sur zone de rangement
                        caisseSurZone = new Image_Rangement_Bon( new Position( indiceX, indiceY ) );
                        listeCaisseSurZone.add( caisseSurZone );
                        indiceX = indiceX + espace;
                    }
                    break;
                    case ' ':{
                        indiceX = indiceX + espace;
                    }
                    break;
                }
            }
            indiceX = 100;
            indiceY = indiceY + 41;
        }
        this.label.setText("score : " + this.plateau.getScore());
    }


    // Dessine les objets
    public void paintComponent(Graphics g){
        g.clearRect(30, 30, 600, 600);
        this.init();
        // Mur
        for(int i = 0; i < listeMur.size(); i++){
            g.drawImage(this.mur.getMur(), listeMur.get(i).getPosition().getX(), listeMur.get(i).getPosition().getY(), this);
        }
        // Caisse
        for(int i = 0; i < listeCaisse.size(); i++){
            g.drawImage(this.caisse.getCaisse(), listeCaisse.get(i).getPosition().getX(), listeCaisse.get(i).getPosition().getY(), this);
        }
        // Zone de rangement
        for(int i = 0; i < listeDestination.size(); i++){
            g.drawImage(this.zoneRangement.getdestination(), listeDestination.get(i).getPosition().getX(), listeDestination.get(i).getPosition().getY(), this);
        }
        // Caisse sur zone
        for(int i = 0; i < listeCaisseSurZone.size(); i++){
            g.drawImage(this.caisseSurZone.getCaisse(), listeCaisseSurZone.get(i).getPosition().getX(), listeCaisseSurZone.get(i).getPosition().getY(), this);
        }
        // Personnage
        g.drawImage(this.personnage.getPersonnage(), this.personnage.getPosition().getX(), this.personnage.getPosition().getY(), this);

        }


    @Override
    public void update(Observable o, Object arg){
        repaint();
    }


}