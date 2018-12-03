package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Prêteur sur gages (Moneylender)
 * 
 * Écartez une carte Cuivre de votre main.
 * Dans ce cas, +3 Pièces.
 */
public class Moneylender extends ActionCard {
	public Moneylender(){super("Moneylender", 4);}

	public void play(Player p){
		CardList list = new CardList();
		for(Card c : p.cardsInHand()){
			if(c.getName().equals("Copper")) list.add(c);
		}
		if (!list.isEmpty()){
			String str = p.chooseCard("Ecartez une carte 'Cuivre' de votre main pour obtenir 3 Pièces ce tour", list, false);
			if (str != "" || str != null) {
				p.getGame().addtoTrash(p.removeFromHand(str));
				p.incrementMoney(3);
			}
		}
	}
}