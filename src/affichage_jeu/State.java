package affichage_jeu;
import java.util.HashSet;

public class State {

	HashSet<Coordonnes> boxes;
	Coordonnes player;
	
	public State(HashSet<Coordonnes> boxes, Coordonnes player) {
		this.boxes = boxes;
		this.player = player;
	}
	

	@Override
	public int hashCode() {
		int result = 17;
		for (Coordonnes b : boxes) {
			result = 37 * result + b.hashCode();
		}
		result = 37 * result + player.hashCode();
		return result;
	}
	

	@Override
	public boolean equals(Object object){
		
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass()) return false;
	    State s = (State)object;
	    if(this.hashCode()== s.hashCode()) return true;
	    if((this.boxes == s.boxes) && (this.player == s.player)) return true;
	    return false;
	}
	
}