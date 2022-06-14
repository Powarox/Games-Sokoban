package game;

public class Player extends Objet_Deplacable {

    public Player(Position p){
        super(p);
    }

    public Position getPosition(){
        return this.position;
    }
}
