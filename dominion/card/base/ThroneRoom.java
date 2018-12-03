package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Salle du trône (Throne Room)
 * 
 * Choisissez 1 carte Action de votre main.
 * Jouez-la deux fois.
 */
public class ThroneRoom extends ActionCard {
	public ThroneRoom(){super("Throne Room", 4);}

	public void play(Player p){
		String str = p.chooseCard("Choississez la carte Action de votre choix et jouez-la deux fois", p.getActionCards(), false);
		Card c2 = null;
		for(Card c : p.cardsInHand()){
			if (c.getName().equals(str)) c2 = c;
		}
		p.playCard(str);
		c2.play(p);
	}
}