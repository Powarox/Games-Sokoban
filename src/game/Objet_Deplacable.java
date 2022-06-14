package game;

public class Objet_Deplacable extends Objet_Statique {

    public Objet_Deplacable(Position p){
        super(p);
    }

    public void setPosition(Position position){
        this.position = position;
    }
}