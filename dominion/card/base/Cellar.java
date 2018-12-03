package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Cave (Cellar)
 * 
 * +1 Action.
 * Défaussez autant de cartes que vous voulez.
 * +1 Carte par carte défaussée.
 */
public class Cellar extends ActionCard {
	public Cellar(){super("Cellar", 2);}

	public void play(Player p){
		p.incrementActions(1);
		int cpt = 0;
		while(true){
			String str = p.chooseCard("Défaussez autant de carte que vous voulez", p.cardsInHand(),true);
			if (str.equals("")) break;
			Card c = p.cardsInHand().getCard(str);
			if(c != null) {
				p.discardFromHand(c);
				cpt++;
			}
		}
		for(int i = 0; i < cpt; i++){
			p.drawToHand();
		}
	}
}