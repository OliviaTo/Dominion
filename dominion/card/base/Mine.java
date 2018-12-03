package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Mine
 * 
 * Écartez une carte Trésor de votre main. Recevez une carte Trésor coûtant jusqu'à 3 Pièces de plus ; ajoutez cette carte à votre main.
 */
public class Mine extends ActionCard {
	public Mine(){super("Mine", 5);}

	public void play(Player p){
		Game g = p.getGame();
		String str = p.chooseCard("Choississez une carte Trésor à écarter", p.getTreasureCards(), false);
		if(str != ""){
			Card c = p.removeFromHand(str);
			g.addtoTrash(c);
			int MoneyAvailable = c.getCost() + 3;

			CardList achat = new CardList();
			for(Card c2 : g.availableSupplyCards()){
				if(c2.getCost() > MoneyAvailable) continue;
				for(CardType ct : c2.getTypes()){
					if (ct.equals(CardType.Treasure)) achat.add(c2);
				}
			}
			str = p.chooseCard("Choississez la carte Trésor à gagner (3 de plus que la carte écartée au maximum)", achat, false);
			p.addToHand(p.getGame().removeFromSupply(str));
		}
	}
}	