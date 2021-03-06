package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Réaction
 * Rmq: les cartes Réaction sont toutes des cartes Action
 */
public abstract class ReactionCard extends ActionCard {
	public ReactionCard(String name, int cost){
		super(name, cost);
	}
	public List<CardType> getTypes(){
		List<CardType> c = new ArrayList<CardType>();
		c.add(CardType.Action);
		c.add(CardType.Reaction);
		return c;
	}
}