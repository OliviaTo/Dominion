package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Sorcière (Witch)
 * 
 * +2 Cartes.
 * Tous vos adversaires recoivent une carte Curse.
 */
public class Witch extends AttackCard {
	public Witch(){super("Witch", 3);}

	public void play(Player p){
		p.drawToHand();
		p.drawToHand();
		for(Player pl : p.otherPlayers()){
			pl.gain("Curse");
		}
	}
}