package affichage_jeu;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Sokoban {
	
	private HashSet<Coordonnes> walls;
	private HashSet<Coordonnes> goals;
	private HashSet<Coordonnes> boxes;
	
	private Coordonnes player;
	private Probleme prob;
	private Heuristics h;
	
	private int ligne;
	private int colonne;
	
	public Sokoban() {
	}
	

	public int loadFile(String filename, char hChoice) throws FileNotFoundException,
			NumberFormatException, NoSuchElementException {
		
		colonne = 0;
		int numPlayer = 0;
		walls = new HashSet<Coordonnes>();
		goals = new HashSet<Coordonnes>();
		boxes = new HashSet<Coordonnes>();
		Scanner s = new Scanner(new File(filename));
		ligne = Integer.parseInt(s.nextLine());
		for (int i = 0; i< ligne; i++) {
			String next = s.nextLine();
			for (int j=0; j<next.length(); j++) {
				char c = next.charAt(j);
				if (c=='#') //Mur
					walls.add(new Coordonnes(i, j));
				if (c == '@' || c == '+') { //Joueur
					player = new Coordonnes(i, j);
					numPlayer++;
				}
				if (c == '.' || c == '+' || c == '*') //Objectif
					goals.add(new Coordonnes(i, j));
				if (c == '$' || c == '*') //boxes
					boxes.add(new Coordonnes(i,j));
			}
			if (next.length() > colonne)
				colonne = next.length();
		}
		prob = new Probleme(walls, new State(boxes, player), goals);
		h = new Heuristics(goals, hChoice);
		System.out.println("ligne: " + ligne + ", colonne: " + colonne);
		return numPlayer;
	}


	public String solve(char method) {
		Recherche s = new Recherche(h);
		switch(method) {

		case 'u':
			return s.prioritySearch(prob, 'u');
		case 'a':
			return s.prioritySearch(prob, 'a');	
		case 'g':
			return s.prioritySearch(prob, 'g');
		default:
			return "Invalid method, please choose a valid search method.";
		}
	}

	public int getligne() {
		return ligne;
	}
	
	public int getColonne() {
		return colonne;
	}

	public HashSet<Coordonnes> getWalls() {
		return walls;
	}
	
	public HashSet<Coordonnes> getBoxes() {
		return boxes;
	}
	
	public HashSet<Coordonnes> getGoals() {
		return goals;
	}
	
	public Coordonnes getPlayer() {
		return player;
	}
}
