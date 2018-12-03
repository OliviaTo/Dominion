package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Atelier (Workshop)
 * 
 * Recevez une carte coûtant jusqu'à 4 Pièces.
 */
public class Workshop extends ActionCard {
	public Workshop(){super("Workshop", 3);}

	public void play(Player p){
		CardList list = new CardList();
		for(Card c : p.getGame().availableSupplyCards()){
			if(c.getCost() <= 4) list.add(c);
		}
		String str = p.chooseCard("Recevez une carte coûtant 4 Pièces ou moins", list, false);
		p.gain(str);
	}
}