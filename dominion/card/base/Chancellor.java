package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chancellier (Chancellor)
 * 
 * +2 Pièces.
 * Vous pouvez immédiatement défausser votre deck.
 */
public class Chancellor extends ActionCard {	
	public Chancellor(){super("Chancellor", 3);}

	public void play(Player p){
		p.incrementMoney(2);
		ArrayList<String> ts = new ArrayList();
		ts.add("y");
		ts.add("n");
		String s = p.choose("Voulez vous défausser votre deck ? (y/n)", ts,false);
		if (s.equals("y")) {
			CardList adef = new CardList();
			Card c;
			do {
				c = p.drawCard();
				if(c != null) adef.add(c);
			}
			while(c != null);
			for(Card c2 : adef){
				p.discardCard(c2);
			}
		}
	}
}