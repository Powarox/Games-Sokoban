package affichage_jeu;
public class Noeud {

	public Noeud parent;
	public State state;
	public int cost;
	public String move;
	
	public Noeud(State state, Noeud parent, int cost, String move) {
		this.state = state;
		this.parent = parent;
		this.cost = cost;
		this.move = move;
	}

	@Override
	public boolean equals(Object n) {
		return (this.state == ((Noeud) n).state);
	}

}
