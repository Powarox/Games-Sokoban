package affichage_jeu;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Recherche {

	//search strings

    private static final String UCS = "Uniform-Cost Recherche";
    private static final String ASTAR = "A* Recherche";
    private static final String GREEDY = "Greedy Recherche";
    
    private static Heuristics h;
	
	public Recherche(Heuristics h) {
		Recherche.h = h;
	}

	public String prioritySearch(Probleme p, char choice) {
		String method = UCS;
		boolean isUCS = true;
		long startTime = System.currentTimeMillis();
		int totalNode = 1;
		int redundant = 0;
		Noeud initial = new Noeud(p.initialState, null, 0, "");
		Set<State> explored = new HashSet<State>();
		//check search method to see if greedy or A* is chosen
		Queue<Noeud> fringe = new PriorityQueue<Noeud>(11, costComparator);
		if (choice == 'g') {
			fringe = new PriorityQueue<Noeud>(11, greedyComparator);
			method = GREEDY;
			isUCS = false;
		}
		if(choice == 'a') {
			fringe = new PriorityQueue<Noeud>(11, astarComparator);
			method = ASTAR;
			isUCS = false;
		}
		fringe.add(initial);
		while (!fringe.isEmpty()) {
			Noeud n = fringe.remove();
			if (p.goalTest(n.state))
				return getSolution(method, n, totalNode, redundant, fringe.size(), explored.size(), System.currentTimeMillis() - startTime);
			if (!p.deadlockTest(n.state)) { //check for deadlock
				explored.add(n.state);
				ArrayList<String> actions = p.actions(n.state);
				for (int i=0; i<actions.size(); i++) {
					Noeud child = getChild(p, n, actions.get(i), isUCS);
					if((child!=null) && (child.state!=null)) {
						totalNode++;
						if ((!explored.contains(child.state))&&(!fringe.contains(child)))
								fringe.add(child);
						else {
							redundant++;
							//check if fringe contains current state and compare the cost
							for (Noeud next : fringe) {
								if (next == child) 
									if(child.cost < next.cost) {
										next = child;
									}
							}
						}
					}
				}
			}
		}
		return getSolution(method, null, totalNode, redundant, fringe.size(), explored.size(), System.currentTimeMillis() - startTime);
	}
	// retourne lq solution
	private String getSolution(String method, Noeud n, int totalNode, int redundant, int fringeSize, int exploredSize, long totalTime) {
		String result = "";
		int steps = 0;
		if (n == null)
			result = "Failed to solve the puzzle";
		else
			while (n.parent!=null) {
				result = n.move + " " + result;
				n = n.parent;
				steps++;
			}
		result =  method + ":\n" + result + "\n(total of " + steps + " steps)";


		return result;
	}

	private Noeud getChild(Probleme p, Noeud n, String action, boolean isUcs) {
		@SuppressWarnings("unchecked")
		HashSet<Coordonnes> boxes = (HashSet<Coordonnes>) n.state.boxes.clone();
		int row = n.state.player.ligne;
		int col = n.state.player.colonne;
		int newCost = n.cost+1;
		Coordonnes newPlayer = n.state.player;
		char choice = action.charAt(0);
		switch(choice) {
			case 'u':
				//update player coordinate
				newPlayer = new Coordonnes(row-1, col);
				//check if player is pushing a box
				if (boxes.contains(newPlayer)) {
					Coordonnes newBox = new Coordonnes(row-2, col);
					//update box coordinate
					boxes.remove(newPlayer);
					boxes.add(newBox);
					if (isUcs)
						newCost++;
				}
				break;
			case 'd':
				//update player coordinate
				newPlayer = new Coordonnes(row+1, col);
				//check if player is pushing a box
				if (boxes.contains(newPlayer)) {
					Coordonnes newBox = new Coordonnes(row+2, col);
					//update box coordinate
					boxes.remove(newPlayer);
					boxes.add(newBox);
					if (isUcs)
						newCost++;
				}
				break;
			case 'l':
				//update player coordinate
				newPlayer = new Coordonnes(row, col-1);
				//check if player is pushing a box
				if (boxes.contains(newPlayer)) {
					Coordonnes newBox = new Coordonnes(row, col-2);
					//update box coordinate
					boxes.remove(newPlayer);
					boxes.add(newBox);
					if (isUcs)
						newCost++;
				}
				break;
			case 'r':

				newPlayer = new Coordonnes(row, col+1);

				if (boxes.contains(newPlayer)) {
					Coordonnes newBox = new Coordonnes(row, col+2);

					boxes.remove(newPlayer);
					boxes.add(newBox);
					if (isUcs)
						newCost++;
				}
				break;
		}
		return new Noeud(new State(boxes, newPlayer), n, newCost, Character.toString(choice));
	}
	

	public static Comparator<Noeud> astarComparator = new Comparator<Noeud>() {
		@Override
		public int compare(Noeud n1, Noeud n2) {
            return (int) ((n1.cost + h.getHeuristic(n1.state)) - (n2.cost + h.getHeuristic(n2.state)));
        }
	};
	

	public static Comparator<Noeud> costComparator = new Comparator<Noeud>(){
		@Override
		public int compare(Noeud n1, Noeud n2) {
            return (int) (n1.cost - n2.cost);
        }
	};
	public static Comparator<Noeud> greedyComparator = new Comparator<Noeud>() {
		@Override
		public int compare(Noeud n1, Noeud n2) {
            return (int) ((h.getHeuristic(n1.state)) - (h.getHeuristic(n2.state)));
        }
	};
	
}
