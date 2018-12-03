import java.util.*;
import dominion.*;
import dominion.card.*;
import dominion.card.base.*;

/**
 * Classe pour l'exécution d'une partie de Dominion
 */
class Main {
	public static void main(String[] args) {
		// Noms des joueurs de la partie
		// (le nombre total de joueurs correspond au nombre de noms dans le 
		// tableau)
		String[] playerNames = new String[]{"Marco", "Polo"};
		// Prépare les piles "royaume" de la réserve (hors cartes communes)
		List<CardList> kingdomStacks = new ArrayList<CardList>();

		CardList[] v = new CardList[10];
		for(int i = 0; i < 10; i++){
			v[i] = new CardList();
		}
		for(int i = 0; i < 10; i++) {
			v[0].add(new Village());
			v[1].add(new Festival());
			v[2].add(new Market());
			v[3].add(new Gardens());
			v[4].add(new CouncilRoom());
			v[5].add(new Woodcutter());
			v[6].add(new Smithy());
			v[7].add(new ThroneRoom());
			v[8].add(new Adventurer());
			v[9].add(new Cellar());
		}		
		for(int i = 0; i < 8; i++){
			kingdomStacks.add(v[i]);
		}
		
		// Instancie et exécute une partie
		Game g = new Game(playerNames, kingdomStacks);
		g.run();
	}
}