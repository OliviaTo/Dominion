package dominion;
import java.util.*;
import dominion.card.*;
import dominion.card.common.*;

/**
 * Class représentant une partie de Dominion
 */
public class Game {
	// Tableau contenant les joueurs de la partie
	private Player[] players;
	
	// Index du joueur dont c'est actuellement le tour
	private int currentPlayerIndex;
	
	/* Liste des piles dans la réserve du jeu.
	 * On suppose ici que toutes les listes contiennent des copies de la même
	 * carte.
	 * Ces piles peuvent être vides en cours de partie si toutes les cartes de 
	 * la pile ont été achetées ou gagnées par les joueurs.*/
	private List<CardList> supplyStacks;
	
	// Liste des cartes qui ont été écartées (trash)
	private CardList trashedCards;
	
	private Scanner scanner;
	

	public Game(String[] playerNames, List<CardList> kingdomStacks) {
		this.scanner = new Scanner(System.in);
		this.trashedCards = new CardList();


		this.supplyStacks = new ArrayList<CardList>();
		this.supplyStacks.addAll(kingdomStacks);

		CardList coppers, silvers, golds, estates, duchies, provinces, curses;
		coppers = new CardList();
		silvers = new CardList();
		golds = new CardList();
		estates = new CardList();
		duchies = new CardList();
		provinces = new CardList();
		curses = new CardList();

		int nbVic, nbCur;
		if (playerNames.length < 3) nbVic = 8;
		else nbVic = 12;
		nbCur = 10 * (playerNames.length-1);

		for (int i = 0; i < playerNames.length * 3; i++) estates.add(new Estate());

		for (int i = 0; i < 60; i++) {
			coppers.add(new Copper());
			if (i < 40) silvers.add(new Silver());
			if (i < 30) golds.add(new Gold());
			if (i < nbVic){
				estates.add(new Estate());
				duchies.add(new Duchy());
				provinces.add(new Province());
			} 
			if (i < nbCur) curses.add(new Curse());
		}

		this.supplyStacks.add(coppers);
		this.supplyStacks.add(silvers);
		this.supplyStacks.add(golds);
		this.supplyStacks.add(estates);
		this.supplyStacks.add(duchies);
		this.supplyStacks.add(provinces);
		this.supplyStacks.add(curses);
		System.out.println(this.supplyStacks.size());

		this.players = new Player[playerNames.length];
		for(int i = 0; i < playerNames.length; i++){
			this.players[i] = new Player(playerNames[i], this);
		}
	
	}

	public void addtoTrash(Card c){this.trashedCards.add(c);}
	

	public Player getPlayer(int i) {return this.players[i];}
	
	public int numberOfPlayers() {return this.players.length;}
	
	private int indexOfPlayer(Player p) {
		for(int i = 0; i < numberOfPlayers(); i++){
			if (this.players[i] == p) return i;
		}
		return -1;
	}
	
	public List<Player> otherPlayers(Player p) {
		List<Player> adversaires = new ArrayList<Player>(numberOfPlayers());
		for(int i = indexOfPlayer(p) + 1; i < numberOfPlayers(); i++){
			adversaires.add(this.players[i]);
		}
		for(int i = 0; i < indexOfPlayer(p); i++){
			adversaires.add(this.players[i]);
		}
		return adversaires;
	}
	
	/**
	 * Renvoie la liste des cartes qui sont disponibles à l'achat dans la 
	 * réserve.
	 * 
	 * @return une liste de cartes contenant la première carte de chaque pile 
	 * non-vide de la réserve (cartes royaume et cartes communes)
	 */
	public CardList availableSupplyCards() {
		CardList rtn = new CardList();
		for(CardList l : this.supplyStacks){
			if (l.size() > 0){
				rtn.add(l.get(0));
			}
		}
		return rtn;
	}
	
	/**
	 * Renvoie une représentation de l'état de la partie sous forme d'une chaîne
	 * de caractères.
	 * 
	 * Cette représentation comporte
	 * - le nom du joueur dont c'est le tour
	 * - la liste des piles de la réserve en indiquant pour chacune :
	 *   - le nom de la carte
	 *   - le nombre de copies disponibles
	 *   - le prix de la carte
	 *   si la pile n'est pas vide, ou "Empty stack" si la pile est vide
	 */
	public String toString() {
		Player currentPlayer = this.players[this.currentPlayerIndex];
		String r = String.format("     -- %s's Turn --\n", currentPlayer.getName());
		for (List<Card> stack: this.supplyStacks) {
			if (stack.isEmpty()) {
				r += "[Empty stack]   ";
			} else {
				Card c = stack.get(0);
				r += String.format("%s x%d(%d)   ", c.getName(), stack.size(), c.getCost());
			}
		}
		r += "\n";
		return r;
	}
	
	/**
	 * Renvoie une carte de la réserve dont le nom est passé en argument.
	 * 
	 * @param cardName nom de la carte à trouver dans la réserve
	 * @return la carte trouvée dans la réserve ou {@code null} si aucune carte 
	 * ne correspond
	 */
	public Card getFromSupply(String cardName) {
		for(CardList l : this.supplyStacks){
			if (l.size() > 0) {
				if (l.get(0).getName().equals(cardName)) return l.get(0);
			}
		}
		return null;
	}
	
	/**
	 * Retire et renvoie une carte de la réserve
	 * 
	 * @param cardName nom de la carte à retirer de la réserve
	 * @return la carte retirée de la réserve ou {@code null} si aucune carte
	 * ne correspond au nom passé en argument
	 */
	public Card removeFromSupply(String cardName) {
		for(CardList l : this.supplyStacks){
			if (l.size() > 0) {
				if (l.get(0).getName().equals(cardName)) return l.remove(0);
			}
		}
		return null;
	}
	
	/**
	 * Teste si la partie est terminée
	 * 
	 * @return un booléen indiquant si la partie est terminée, c'est-à-dire si
	 * au moins l'unedes deux conditions de fin suivantes est vraie
	 *  - 3 piles ou plus de la réserve sont vides
	 *  - la pile de Provinces de la réserve est vide
	 * (on suppose que toute partie contient une pile de Provinces, et donc si 
	 * aucune des piles non-vides de la réserve n'est une pile de Provinces, 
	 * c'est que la partie est terminée)
	 */
	public boolean isFinished() {
		if(getFromSupply("Province") == null) return true;
		int c = 0;
		for(CardList l : this.supplyStacks){
			if(l.size() == 0) c++;
		}


		if(c >= 3) return true;
		return false;
	}
	
	/**
	 * Boucle d'exécution d'une partie.
	 * 
	 * Cette méthode exécute les tours des joueurs jusqu'à ce que la partie soit
	 * terminée. Lorsque la partie se termine, la méthode affiche le score 
	 * final et les cartes possédées par chacun des joueurs.
	 */
	public void run() {
		while (! this.isFinished()) {
			// joue le tour du joueur courant
			this.players[this.currentPlayerIndex].playTurn();
			// passe au joueur suivant
			this.currentPlayerIndex += 1;
			if (this.currentPlayerIndex >= this.players.length) {
				this.currentPlayerIndex = 0;
			}
		}
		System.out.println("Game over.");
		// Affiche le score et les cartes de chaque joueur
		for (int i = 0; i < this.players.length; i++) {
			Player p = this.players[i];
			System.out.println(String.format("%s: %d Points.\n%s\n", p.getName(), p.victoryPoints(), p.totalCards().toString()));
		}
	}
	
	/**
	 * Lit une ligne de l'entrée standard
	 * 
	 * C'est cette méthode qui doit être appelée à chaque fois qu'on veut lire
	 * l'entrée clavier de l'utilisateur (par exemple dans Player.choose), ce
	 * qui permet de n'avoir qu'un seul Scanner pour tout le programme
	 * 
	 * @return une chaîne de caractères correspondant à la ligne suivante de
	 * l'entrée standard (sans le retour à la ligne final)
	 */
	public String readLine() {
		return this.scanner.nextLine();
	}
	
}