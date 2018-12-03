package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Malédiction
 */
public abstract class CurseCard extends Card {
	public CurseCard(String name, int cost){
		super(name, cost);
	}

	public void play(Player p){}

	public List<CardType> getTypes(){
		List<CardType> c = new ArrayList<CardType>();
		c.add(CardType.Curse);
		return c;
	}
}