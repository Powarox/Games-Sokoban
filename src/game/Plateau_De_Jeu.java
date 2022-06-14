package game;
import java.io.File;
import java.util.Observable;
import javax.swing.JFileChooser;


public class Plateau_De_Jeu extends Observable{
    private char[][] plateau;
    private Player player;
    private long score = 0;
    private int cpt = 1;


    public Plateau_De_Jeu(char[][] plateau,Player player){
        this.plateau = plateau;
        this.player = player;
    }

    // Getters et Setters
    public char[][] getPlateau(){
        return this.plateau;
    }
    public void setPlateau(char[][] plateau){
        this.plateau = plateau;
    }
    public Player getPlayer(){
        return this.player;
    }
    public void setPlayer(Player p){
        this.player = p;
    }
    public long getScore(){
        return score;
    }
    public void setScore(long score){
        this.score = score;
    }


    public void deplacement(String direction){
        int y = 0;
        int x = 0;
        switch(direction){
            case "haut": y = -1;
                x = 0;
                break;
            case "bas": y = 1;
                x = 0;
                break;
            case "gauche": y = 0;
                x = -1;
                break;
            case "droit": y = 0;
                x = 1;
                break;
        }
        Position position = this.player.getPosition();
        Position p = new Position(this.player.getPosition().getX(), this.player.getPosition().getY());
        Boolean onDestination = this.plateau[position.getY()][position.getX()] == '+';
        switch(this.plateau[position.getY()+y][position.getX()+x]){
            case '#':
                break;
            case '$':
                switch(this.plateau[position.getY()+y*2][position.getX()+x*2]){
                    case '#':
                    case '$':
                        break;
                    case '.':
                        if(onDestination){
                            this.plateau[position.getY()][position.getX()] = '.';
                        }
                        else{
                            this.plateau[position.getY()][position.getX()] = ' ';
                        }
                        this.plateau[position.getY()+y][position.getX()+x] = '@';
                        this.plateau[position.getY()+y*2][position.getX()+x*2] = '*';
                        this.player.setPosition(new Position(position.getX() + x, position.getY() + y));
                        this.score++;
                        break;
                    case '*':
                        break;
                    case ' ':
                        if(onDestination){
                            this.plateau[position.getY()][position.getX()] = '.';
                        }
                        else{
                            this.plateau[position.getY()][position.getX()] = ' ';
                        }
                        this.plateau[position.getY()+y][position.getX()+x] = '@';
                        this.plateau[position.getY()+y*2][position.getX()+x*2] = '$';
                        this.player.setPosition(new Position(position.getX()+x,position.getY()+y));
                        this.score++;
                        break;
                }
                break;
            case '.':
                if(onDestination){
                    this.plateau[position.getY()][position.getX()] = '.';
                }
                else{
                    this.plateau[position.getY()][position.getX()] = ' ';
                }
                this.plateau[position.getY()+y][position.getX()+x] = '+';
                this.player.setPosition(new Position(position.getX() + x,position.getY() + y));
                this.score++;
                break;
            case '*':
                switch(this.plateau[position.getY() + y * 2][position.getX() + x * 2]){
                    case '#':
                    case '$':
                        break;
                    case '.':
                        if(onDestination){
                            this.plateau[position.getY()][position.getX()] = '.';
                        }
                        else{
                            this.plateau[position.getY()][position.getX()] = ' ';
                        }
                        this.plateau[position.getY() + y][position.getX() + x] = '+';
                        this.plateau[position.getY() + y * 2][position.getX() + x * 2] = '*';
                        this.player.setPosition(new Position(position.getX() + x,position.getY() + y));
                        this.score++;
                        break;
                    case '*':
                        break;
                    case ' ':
                        if(onDestination){
                            this.plateau[position.getY()][position.getX()] = '.';
                        }
                        else{
                            this.plateau[position.getY()][position.getX()] = ' ';
                        }
                        this.plateau[position.getY() + y][position.getX() + x] = '+';
                        this.plateau[position.getY() + y * 2][position.getX() + x * 2] = '$';
                        this.player.setPosition(new Position(position.getX() + x,position.getY() + y));
                        this.score++;
                        break;
                }
                break;
            case ' ':
                if(onDestination){
                    this.plateau[position.getY()][position.getX()] = '.';
                }
                else{
                    this.plateau[position.getY()][position.getX()] = ' ';
                }
                this.plateau[position.getY() + y][position.getX() + x] = '@';
                this.player.setPosition(new Position(position.getX() + x,position.getY() + y));
                this.score++;
                break;
        }
        this.setChanged();
        this.notifyObservers();
    }


    public boolean isFinished(){
        for(int i = 0; i < this.plateau.length; i++){
            for(int j = 0; j < this.plateau[i].length; j++){
                if(this.plateau[i][j] =='$'){
                    return false;
                }
            }
        }
        return true;
    }


    public void afficheTableau(){
        for(int i = 0; i < this.plateau.length; i++){
            for(int j = 0; j < this.plateau.length; j++){
                System.out.print(this.plateau[i][j]);
            }
        }
    }


    public File pickMe(){
        JFileChooser filechooser = new JFileChooser();
        int returnVal=filechooser.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION){
            file = filechooser.getSelectedFile();
        }
        return file;
    }


    public void relancerJeu(){
        String chemin = "niveau" + cpt + ".sok";
        ReadFile newLevel = new ReadFile(chemin);
        this.setPlateau(newLevel.matrice());
        player = new Player(newLevel.positionPersonnage(this.getPlateau()));
        this.setChanged();
        this.notifyObservers();

    }

    public void relancerMenu(){
        System.out.println("Relancer Menu");
    }


    public void suivant(int niv){
        cpt = niv;
        String chemin = "niveau" + niv + ".sok";
        ReadFile newLevel = new ReadFile(chemin);
        this.setPlateau(newLevel.matrice());
        player = new Player(newLevel.positionPersonnage(this.getPlateau()));
        this.setChanged();
        this.notifyObservers();
        System.out.println("Load fichier : " + niv);
    }

}
