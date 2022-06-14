package affichage_jeu;
// Coordonnes pour les Boxes player Mur et Destination

public class Coordonnes {
	
	public int ligne;
	public int colonne;
	
	public Coordonnes(int row, int colonne) {
		this.ligne = row;
		this.colonne = colonne;
	}
	

	@Override
	public int hashCode() {
		return ligne *1000 + colonne;
	}

	@Override
	public boolean equals(Object object){
		if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass()) return false;
		Coordonnes c = (Coordonnes) object;
		if(this.hashCode()== c.hashCode()) return true;
	    return ((this.ligne == c.ligne) && (this.colonne == c.colonne));
	}
	
}
