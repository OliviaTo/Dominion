package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Bureaucrate (Bureaucrat)
 * 
 * Recevez une carte Argent; placez-la sur votre deck.
 * Tous vos adversaires dévoilent une carte Victoire et la placent sur leur deck (sinon ils dévoilent leur main afin que vous puissiez voir qu'ils n'ont pas de cartes Victoire).
 */
public class Bureaucrat extends AttackCard {
	public Bureaucrat(){super("Bureaucrat", 4);}

	public void play(Player p){
		Card c = p.gainToTopDeck("Silver");

		for(Player pl : p.otherPlayers()){
			String str = pl.chooseCard("Montrez un carte Victoire", pl.getVictoryCards(), false);
			if(str.equals("")){
				String s = pl.getName() + " n'a pas de carte Victoire donc il montre ses cartes : ";
				System.out.println(s);
				for (Card c3 : pl.cardsInHand()){
					System.out.print(c3.getName() + "  ");
				}
			} else {
				String s = pl.getName() + "  montre : " + str;
				pl.fromHandToTopDeck(str);
				System.out.println(s);
			}
		}
	}
}