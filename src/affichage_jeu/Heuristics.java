package affichage_jeu;
import java.util.HashSet;


// Cette Classe calcule la distance en le point de depart et larrive et return la distance optimale

public class Heuristics {
	
	private HashSet<Coordonnes> goals;
	double[][] cost;
	HungarianAlgorithm h;
	char hChoice;
	

	public Heuristics(HashSet<Coordonnes> goals, char hChoice) {
		this.goals = goals;
		this.hChoice = hChoice;
		this.cost = new double[goals.size()][goals.size()];
		h = new HungarianAlgorithm(cost.length);
	}
	

	// Cette methode prend deux coordonner et ajoute la somme des différences de lignes et de colonnes
	private int manhattan(Coordonnes c1, Coordonnes c2) {
		return Math.abs(c1.ligne -c2.ligne) + Math.abs(c1.colonne -c2.colonne);
	}

	// Cette methode prend deux coordonner et calcule la distance en utilisant la racine carre fonction sqrt
	private double euclidean(Coordonnes c1, Coordonnes c2) {
		return Math.sqrt((double)((c1.ligne -c2.ligne)*(c1.ligne -c2.ligne)+(c1.colonne -c2.colonne)*(c1.colonne -c2.colonne)));
	}


	public double calculate(State s, String method) {
		HashSet<Coordonnes> boxes = s.boxes;
		double sum = 0;

		//get distance from player to boxes, and add to sum
		Coordonnes player = s.player;
		double playerMin = getDist(player, boxes, method);
		sum+= playerMin;

		//get distance from boxes to goals, add each minimum distance to the sum
		for (Coordonnes b : boxes) {
			double boxMin = getDist(b, goals, method);
			sum += boxMin;
		}

		return sum;
	}


	private double getDist(Coordonnes obj, HashSet<Coordonnes> sets, String method) {
		double minDist = 1000000;
		
		//For each coordinate in a set, calculate the distance according to given heuristic choice
		for (Coordonnes c : sets) {
			double dist;
			if (method.equals("m"))
				dist = manhattan(obj, c);
			else
				dist = euclidean(obj, c);
			if (dist < minDist)
				minDist = dist;
		}
		
		return minDist;
	}
	

	public double getHeuristic(State state) {
		
		if (hChoice == 'm')
			return calculate(state, "m");
		if (hChoice == 'e')
			return calculate(state, "e");
		
		int i=0;
		for (Coordonnes box : state.boxes) {
			int j=0;
			double playerCost = manhattan(state.player, box);
			for (Coordonnes goal : goals) {
				cost[i][j] = manhattan(box, goal);
				cost[i][j] += playerCost;
				j++;
			}
			i++;
		}
		
		int[] result = h.execution(cost);
		double max = 0;
		for (int k=0; k<goals.size(); k++) {
			int goalCol = result[k];
			if (goalCol>-1)
				max += cost[k][goalCol];
		}
		if (hChoice == 'h')
			return max;
		
		return Math.max(Math.max(calculate(state, "m"), calculate(state, "e")), max);
	}

}