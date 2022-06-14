package affichage_jeu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Probleme {

	State initialState;
	HashSet<Coordonnes> walls;
	HashSet<Coordonnes> goals;
	HashMap<Coordonnes, Coordonnes> blocked;
	

	public Probleme(HashSet<Coordonnes> walls, State initialState, HashSet<Coordonnes> goals) {
		this.initialState = initialState;
		this.walls = walls;
		this.goals = goals;
	}


	public boolean goalTest(State state) {
		for(Coordonnes box : state.boxes)
			if (!goals.contains(box))
				return false;
		return true;
	}

	public boolean deadlockTest(State state) {
		for (Coordonnes box : state.boxes) {
			int row = box.ligne;
			int col = box.colonne;
			if (!setContains(goals, row, col)) {
				if (setContains(walls, row-1, col)&&setContains(walls, row, col-1))
					return true; //top & left
				if (setContains(walls, row-1, col)&&setContains(walls, row, col+1))
					return true; //top & right
				if (setContains(walls, row+1, col)&&setContains(walls, row, col-1))
					return true; //bottom & left
				if (setContains(walls, row+1, col)&&setContains(walls, row, col+1))
					return true; //bottom & right

				if (setContains(walls, row-1, col-1)&&setContains(walls, row-1, col)&&
						setContains(walls, row-1, col+1)&&setContains(walls, row, col-2)&&
						setContains(walls, row, col+2)&&(!setContains(goals, row, col-1))&&
								!setContains(goals, row, col+1))
					return true; //top & sides
				if (setContains(walls, row+1, col-1)&&setContains(walls, row+1, col)&&
						setContains(walls, row+1, col+1)&&setContains(walls, row, col-2)&&
						setContains(walls, row, col+2)&&(!setContains(goals, row, col-1))&&
								(!setContains(goals, row, col+1)))
					return true; //bottom & sides
				if (setContains(walls, row-1, col-1)&&setContains(walls, row, col-1)&&
						setContains(walls, row+1, col-1)&&setContains(walls, row-2, col)&&
						setContains(walls, row+2, col)&&(!setContains(goals, row-1, col))&&
								(!setContains(goals, row+1, col)))
					return true; //left & vertical
				if (setContains(walls, row-1, col+1)&&setContains(walls, row, col+1)&&
						setContains(walls, row+1, col+1)&&setContains(walls, row-2, col)&&
						setContains(walls, row+2, col)&&(!setContains(goals, row-1, col))&&
								(!setContains(goals, row+1, col)))
					return true; //right & top/bottom
			}
		}
		return false;
	}

	// Action Valid pour le joueur
	public ArrayList<String> actions(State state) {
		ArrayList<String> actionList = new ArrayList<String>();
		int row = state.player.ligne;
		int col = state.player.colonne;
		HashSet<Coordonnes> boxes = state.boxes;
		// regarde si les 4 deplacement sont valides
		//regarde si c est un mur
		// continue si c est le cas

		Coordonnes newPlayer = new Coordonnes(row-1,col);
		Coordonnes newBox = new Coordonnes(row-2, col);
		if (!walls.contains(newPlayer))
			if (boxes.contains(newPlayer)&&(boxes.contains(newBox)||walls.contains(newBox)))
				;
			else
				actionList.add("u");
		newPlayer = new Coordonnes(row,col+1);
		newBox = new Coordonnes(row, col+2);
		if (!walls.contains(newPlayer))
			if (boxes.contains(newPlayer)&&(boxes.contains(newBox)||walls.contains(newBox)))
				;
			else
				actionList.add("r");
		newPlayer = new Coordonnes(row+1,col);
		newBox = new Coordonnes(row+2, col);
		if (!walls.contains(newPlayer))
			if (boxes.contains(newPlayer)&&(boxes.contains(newBox)||walls.contains(newBox)))
				;
			else
				actionList.add("d");
		newPlayer = new Coordonnes(row,col-1);
		newBox = new Coordonnes(row, col-2);
		if (!walls.contains(newPlayer))
			if (boxes.contains(newPlayer)&&(boxes.contains(newBox)||walls.contains(newBox)))
				;
			else
				actionList.add("l");
		return actionList;
	}

	private boolean setContains(HashSet<Coordonnes> set, int row, int col) {
		if (set.contains(new Coordonnes(row, col)))
			return true;
		return false;
	}
	
}
