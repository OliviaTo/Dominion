package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Victoire
 */
public abstract class VictoryCard extends Card {
	public VictoryCard (String name, int cost){
		super(name, cost);
	}
	public void play(Player p){}

	public List<CardType> getTypes(){
		List<CardType> c = new ArrayList<CardType>();
		c.add(CardType.Victory);
		return c;
	}
}