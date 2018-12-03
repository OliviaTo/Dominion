package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Milice (Militia)
 * 
 * 2 Pièces.
 * Tous vos adversaires défaussent leurs cartes de façon à n'avoir que 3 cartes en main.
 */
public class Militia extends AttackCard {
	public Militia(){super("Militia", 4);}

	public void play(Player p){
		p.incrementMoney(2);
		for(Player pl : p.otherPlayers()){
			while (pl.cardsInHand().size() > 3){
				String str = p.chooseCard("Vous devez défausser jusqu'à avoir 3 cartes", pl.cardsInHand(), false);
				for(Card c : pl.cardsInHand()){
					if(c.getName().equals(str)) {
						pl.discardFromHand(c); break;
					}
				}
			}
		}
	}
}