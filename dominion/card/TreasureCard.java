package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Trésor
 */
public abstract class TreasureCard extends Card {
	public TreasureCard(String name, int cost){
		super(name, cost);
	}

	public List<CardType> getTypes(){
		List<CardType> c = new ArrayList<CardType>();
		c.add(CardType.Treasure);
		return c;
	}

	public void play(Player p){
		p.incrementMoney(treasureValue());
	}

	public abstract int treasureValue();

}