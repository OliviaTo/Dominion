package dominion.card;
import java.util.*;
import dominion.*;

// Représentation des cartes du jeu Dominion
public abstract class Card {

	private String name;
	
	private int cost;
	

	public Card(String name, int cost) {
		this.name = name;
		this.cost = cost;
	}


	public int getCost() {return this.cost;}
	
	public String getName() {return this.name;}
	

	public List<CardType> getTypes() {
		return new ArrayList<CardType>();
	}
	

	public String toString() {
		return this.name;
	}
	

	public abstract void play(Player p);


	public int victoryValue(Player p) {
		return 0;
	}
}