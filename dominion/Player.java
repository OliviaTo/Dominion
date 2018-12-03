package dominion;
import java.util.*;
import dominion.card.*;
/**
 * Un joueur de Dominion
 */
public class Player {
	/**
	 * Nom du joueur
	 */
	private String name;
	
	/**
	 * Nombre d'actions disponibles
	 */
	private int actions;
	
	/**
	 * Nombre de pièces disponibles pour acheter des cartes
	 */
	private int money;
	
	/**
	 * Nombre d'achats disponibles
	 */
	private int buys;
	
	/**
	 * Référence vers la partie en cours
	 */
	private Game game;
	
	/**
	 * Liste des cartes dans la main du joueur
	 */
	private CardList hand;
	
	/**
	 * Liste des cartes dans la défausse du joueur
	 */
	private CardList discard;
	
	/**
	 * Liste des cartes dans la pioche du joueur
	 */
	private CardList draw;
	
	/**
	 * Listes des cartes qui ont été jouées pendant le tour courant
	 */
	private CardList inPlay;
	

	public Player(String name, Game game) {
		this.name = name;
		this.game = game;

		this.money = 0;
		this.inPlay = new CardList();
		this.draw = new CardList();
		this.discard = new CardList();
		this.hand = new CardList();

		for(int i=0; i<3; i++){
			this.gain("Estate");
		}

		for(int i=0; i<7; i++){
			this.gain("Copper");
		}

		this.endTurn();
	}

	public String getName() {
		return this.name;
	}
	
	public int getActions() {
		return this.actions;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public int getBuys() {
		return this.buys;
	}
	
	public Game getGame() {
		return this.game;
	}
	

	public void incrementActions(int n) {
		this.actions = this.actions + n;
	}
	

	public void incrementMoney(int n) {
		this.money = this.money + n;
	}
	
	
	public void incrementBuys(int n) {
		this.buys = this.buys + n;
	}

	
	public CardList cardsInHand() {
		CardList inHand = new CardList();
		for (int i =0; i< this.hand.size() ; i++){
			inHand.add(this.hand.get(i));
		}
		return inHand;
	}
	
	
	public CardList totalCards() {
		CardList totale = new CardList();
		for(Card c : this.hand){
			totale.add(c);
		}
		for(Card c : this.discard){
			totale.add(c);
		}
		for(Card c : this.draw){
			totale.add(c);
		}
		for(Card c : this.inPlay){
			totale.add(c);
		}
		return totale;
	}
	
	
	public int victoryPoints() {
		int points = 0;
		for(Card c : totalCards()){
			points +=  c.victoryValue(this);
		}
		return points;
	}
	

	public List<Player> otherPlayers() {
		return this.game.otherPlayers(this);
	}
	
	
	public Card drawCard() {
		if(this.draw.isEmpty() && this.discard.isEmpty()){
			return null;
		}
		else if (this.draw.isEmpty()){
			this.discard.shuffle();
			CardList tmp = new CardList(this.discard);
			for(Card c : tmp){

				this.draw.add(this.discard.remove(c.getName()));
			}
		}
		return this.draw.remove(0);
	}

	//Défausse la carte de la main passée en argument
	public void discardFromHand(Card c){
		this.discard.add(this.hand.remove(c.getName()));
	}

	public void fromHandToTopDeck(String cardName){
		Card c = removeFromHand(cardName);
		if(c!=null){
			this.draw.add(0, c);
		}		
	}

	//Défausse la carte passée en argument
	public void discardCard(Card c){
		this.discard.add(c);
	}

	//Ajoute la carte dans la main
	public void addToHand(Card c){
		this.hand.add(c);
	}

	//Pioche et met dans la main directement
	public void drawToHand(){
		this.addToHand(this.drawCard());
	}

	//retire une carte de la main
	public Card removeFromHand(String cardName){
		return this.hand.remove(cardName);
	}
	
	//écarte une carte du jeu
	public void removeFromInPlay(Card c){
		this.inPlay.remove(c);
	}

	public String toString() {
		String r = String.format("     -- %s --\n", this.name);
		r += String.format("Actions: %d     Money: %d     Buys: %d     Draw: %d     Discard: %d\n", this.actions, this.money, this.buys, this.draw.size(), this.discard.size()); 
		r += String.format("In play: %s\n", this.inPlay.toString());
		r += String.format("Hand: %s\n", this.hand.toString());
		return r;
	}
	
	
	public CardList getTreasureCards() {
		CardList treasures = new CardList();
		for (Card c : this.hand){
			if (c instanceof TreasureCard){
				treasures.add(c);
			}
		}
		return treasures;
	}
	

	public CardList getActionCards() {
		CardList actions = new CardList();
		for (Card c : this.hand){
			if (c instanceof ActionCard){
				actions.add(c);
			}
		}
		return actions;
	}
	
	
	public CardList getVictoryCards() {
		CardList victories = new CardList();
		for (Card c : this.hand){
			if (c instanceof VictoryCard){
				victories.add(c);
			}
		}
		return victories;
	}
	

	public void playCard(Card c) {
		this.inPlay.add(this.hand.remove(c.getName()));
		c.play(this);

	}
	
	
	public void playCard(String cardName) {
		if(this.hand.getCard(cardName) != null){
			this.playCard(this.hand.getCard(cardName));
		}
	}
	
	
	public void gain(Card c) {
		if(c != null){
			this.discard.add(c);
		}
	}
	

	public Card gain(String cardName) {
		Card c = this.game.removeFromSupply(cardName);
		if(c!=null){
			this.discard.add(c);
		}
		return c; 
	}

	public Card gainToTopDeck(String cardName) {
		Card c = this.game.removeFromSupply(cardName);
		if(c!=null){
			this.draw.add(0, c);
		}
		return c; 
	}

	public Card buyCard(String cardName) {
		Card c = this.game.getFromSupply(cardName);
		if((c != null) && (this.money >= c.getCost()) && (this.buys >= 1)){
			this.money -= c.getCost();
			this.buys--;
			return this.gain(cardName);
		}else{
			return null;
		}
	}
	

	public String choose(String instruction, List<String> choices, boolean canPass) {
		// La liste de choix est convertie en ensemble pour éviter les doublons
		Set<String> choiceSet = new HashSet<String>();
		for (String c: choices) {
			choiceSet.add(c);
		}
		if (choiceSet.isEmpty()) {
			// Aucun choix disponible
			return "";
		} else if (choiceSet.size() == 1 && !canPass) {
			// Un seul choix possible (renvoyer cet unique élément)
			return choiceSet.iterator().next();
		} else {
			String input;
			// Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
			while (true) {
				System.out.print("\n\n");
				// affiche l'état du jeu
				System.out.print(this.game);
				System.out.print("\n");
				// affiche l'état du joueur
				System.out.print(this);
				System.out.print("\n");
				// affiche l'instruction
				System.out.println(">>> " + instruction);
				System.out.print("> ");
				// lit l'entrée de l'utilisateur au clavier
				input = this.game.readLine();
				if (choiceSet.contains(input) || (canPass && input.equals(""))){
					// si une réponse valide est obtenue, elle est renvoyée
					return input;
				}
			}
		}
	}
	

	public String chooseCard(String instruction, CardList choices, boolean canPass) {
		// liste de noms de cartes
		List<String> stringChoices = new ArrayList<String>();
		for (Card c: choices) {
			// tous les noms sont ajoutés à l'ensemble
			stringChoices.add(c.getName());
		}
		// appel de la méthode précédente en passant l'ensemble de noms
		return this.choose(instruction, stringChoices, canPass);
	}
	

	public void startTurn() {
		this.actions = 1;
		this.buys = 1;
	}
	

	public void endTurn() {
		this.actions = 0;
		this.money = 0;
		this.buys = 0;
		CardList list = new CardList(this.hand);
		for(Card c : list){
			this.discard.add(this.hand.remove(c.getName()));
		}
		list = new CardList(this.inPlay);
		for(Card c : list){
			this.discard.add(this.inPlay.remove(c.getName()));
		}
		for(int i=0; i<5;i++){
			this.drawToHand();
		}
	}
	
	/**
	 * Exécute le tour d'un joueur
	 * 
	 * Cette méthode exécute successivement les 5 phases du tour d'un joueur:
	 * 
	 * 1. (Préparation) la méthode {@code startTurn()} est appelée
	 * 
	 * 2. (Action) Tant que le joueur a des actions disponibles, on lui demande 
	 * de choisir le nom d'une carte Action de sa main à jouer. Il peut passer à
	 * tout moment à la phase suivante (soit de manière forcée s'il n'a plus de 
	 * carte Action en main soit volontairement en entrant la chaîne vide). 
	 * Lorsqu'il joue une carte Action, la fonction décrémente son nombre 
	 * d'actions puis joue la carte.
	 * 
	 * 3. (Trésor) Le joueur joue toutes les cartes Trésor de sa main 
	 * automatiquement (dans le jeu de base il n'y a aucune raison de ne pas 
	 * jouer tous les trésors automatiquement).
	 * 
	 * 4. (Achat) Tant que le joueur a au moins un achat disponible, on lui 
	 * demande de choisir le nom d'une carte de la réserve qu'il veut acheter. 
	 * Il ne peut acheter que des cartes dont le prix est inférieur à l'argent 
	 * dont il dispose. Le joueur peut passer (et terminer son tour) à tout 
	 * moment pendant cette phase.
	 * 
	 * 5. (Fin) La méthode {@code endTurn()} est appelée pour terminer le tour 
	 * du joueur
	 */
	public void playTurn() {
		this.startTurn();

		while(this.actions >= 1){
			String actionChosen = this.chooseCard("Choose an Action card to play (you can pass by hitting enter)", this.getActionCards(), true);
			if(!actionChosen.equals("")){
				this.actions--;
				this.playCard(actionChosen);
			}else{
				break;
			}
		}

		for(Card c : this.getTreasureCards()){
			this.playCard(c);
		}

		while(this.buys >= 1){
			CardList list = new CardList();
			for(Card c : this.game.availableSupplyCards()){
				if(c.getCost() <= this.money) {
					list.add(c);
				}
			}
			String buyChosen = this.chooseCard("Choose a Kingdom card to buy (you can pass by hitting enter)", list, true);
			System.out.println(buyChosen);
			if(buyChosen.equals("")){
				break;
			}
			this.buyCard(buyChosen);
		}

		this.endTurn();
	}
}
