package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Aventurier (Adventurer)
 * 
 * Dévoilez des cartes de votre deck jusqu'à ce que 2 cartes Trésor soient dévoilées. Ajoutez ces cartes Trésor à votre main et défaussez les autres cartes dévoilées.
 */
public class Adventurer extends ActionCard {
	public Adventurer(){super("Adventurer", 6);}

	public void play(Player p){
		int treasure = 0;
		CardList adef = new CardList();
		while(treasure < 2){
			Card c = p.drawCard();
			boolean treas = false;
			for(CardType t : c.getTypes()){
				if (t == CardType.Treasure) treas = true;
			}
			if(treas){
				treasure++;
				p.addToHand(c);
			} else {
				adef.add(c);
			}
		}
		for(Card c : adef){
			p.discardCard(c);
		}
	}
}