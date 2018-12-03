package dominion.card;
import java.util.*;

/**
 * Liste de cartes
 */
public class CardList extends ArrayList<Card> {
	
	// Constructeur vide
	public CardList() {
		super();
	}
	
	// Constructeur à partir d'une liste de cartes
	 
	public CardList(List<Card> l) {
		super(l);
	}
	
	// Mélange la liste
	public void shuffle() {
		Collections.shuffle(this);
	}
	
	public Card remove(String cardName) {
		for (Card c: this) {
			if (c.getName().equals(cardName)) {
				this.remove(c);
				return c;
			}
		}
		return null;
	}
	
	public Card getCard(String cardName) {
		for (Card c: this) {
			if (c.getName().equals(cardName)) {
				return c;
			}
		}
		return null;
	}
	
	public String toString() {
		if (this.size() == 0) {
			return "";
		}
		
		String r = "";
		for (Card c: this) {
			r += c.toString() + ", ";
		}
		return r.substring(0, r.length() - 2);
	}
}
