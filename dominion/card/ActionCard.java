package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Action
 */
public abstract class ActionCard extends Card {
	public ActionCard(String name, int cost){
		super(name, cost);
	}

	public List<CardType> getTypes(){
		List<CardType> c = new ArrayList<CardType>();
		c.add(CardType.Action);
		return c;
	}

}