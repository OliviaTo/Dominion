package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chapelle (Chapel)
 * 
 * Écartez jusqu'à 4 cartes de votre main.
 */
public class Chapel extends ActionCard {	
	public Chapel(){super("Chapel", 2);}

	public void play(Player p){
		for (int i = 0; i < 4; i++){
			String str = p.chooseCard("Voulez vous défausser une carte (vous pouvea en défausser jusqu'à 4)", p.cardsInHand(), true) ;
			if(str.equals("")) break;
			p.getGame().addtoTrash(p.removeFromHand(str));
		}
	}
}