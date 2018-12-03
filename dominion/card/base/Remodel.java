package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Rénovation (Remodel)
 * 
 * Écartez une carte de votre main.
 * Recevez une carte coûtant jusqu'à 2 Pièces de plus que la carte écartée.
 */
public class Remodel extends ActionCard {
	public Remodel(){super("Remodel", 4);}

	public void play(Player p){
		String str = p.chooseCard("Écartez une carte de votre main", p.cardsInHand(), false);
		Card c = p.removeFromHand(str);
		int value = c.getCost() + 2;
		p.getGame().addtoTrash(c);

		CardList list = new CardList();
		for(Card c2 : p.getGame().availableSupplyCards()){
			if (c2.getCost() <= value) list.add(c2);
		}
		str = p.chooseCard("Vous gagnez une carte d'un prix inférieur ou égale à 2 pièces de plus que la carte écartée, choississez", list, false);
		p.gain(str);

	}
}