package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Festin (Feast)
 * 
 * Écartez cette carte.
 * Recevez une carte coûtant jusqu'à 5 Pièces.
 */
public class Feast extends ActionCard {	
	public Feast(){super("Feast", 4);}

	public void play(Player p){
		Game g = p.getGame();
		p.removeFromInPlay(this);
		g.addtoTrash(this);

		CardList list = new CardList();
		for(Card c : g.availableSupplyCards()){
			if (c.getCost() <= 5) list.add(c);
		}
		String str = p.chooseCard("Vous gagnez une carte à 5 pièces ou moins, choississez", list, false);
		p.gain(str);
	}
}