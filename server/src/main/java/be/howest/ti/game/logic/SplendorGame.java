package be.howest.ti.game.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SplendorGame extends GameSuperclass {

    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_FOUR_PLAYERS = 7;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS = 5;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_TWO_PLAYERS = 4;

    private final static int INITIAL_GOLD_AMOUNT = 5;

    private Purse tokenBank; // TODO make final
    private final Set<Noble> unclaimedNobles;
    private final Market market;
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;
    private Noble chosenNoble;

    private static final int ONE_NOBLE = 1;

    public SplendorGame(GameSuperclass gameLobby){
        super(gameLobby);

        this.market = new Market();
        this.currentPlayer = getPlayers().getFirst();
        this.tokenBank = generateTokenBank();
        this.unclaimedNobles = setUnclaimedNobles();
    }

    public Player getWinner() {
        return winner;
    }

    public void endTurn() {
        if (currentPlayer.getPrestigePoints() >= 15) {
            winner = currentPlayer;
        } else {
            currentPlayer = getNextPlayer();
            checkForNoble();
        }
    }

    private Player getNextPlayer() {
        List<Player> players = getPlayers();
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Market getMarket() {
        return market;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player findPlayer(String playerName) {
        List<Player> players = getPlayers();
        Player requestPlayer = null;
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                requestPlayer = player;
            }
        }
        if (requestPlayer == null) {
            throw new IllegalArgumentException("Player not found");
        }
        return requestPlayer;
    }

    public void buyDevelopment(Purse payment, String developmentName, Player player){
        if (playerTurnChecker(player)){
            player.checkIfPaymentIsSufficient(market.findMatchingDevelopmentOverAllLevels(developmentName), payment);
            player.buyDevelopment(market.removeVisibleDevelopment(developmentName), payment);
            tokenBank.addTokens(payment);
            endTurn();
        }
    }

    public void reserveDevelopment(String developmentName, Player player){
        if (playerTurnChecker(player)) {
            player.reserveDevelopment(market.removeVisibleDevelopment(developmentName));
            givePlayerGoldTokenIfPossible(player);
            endTurn();
        }
    }

    private void givePlayerGoldTokenIfPossible(Player player) {
        if (tokenBank.getTokens().get(Token.GOLD) > 0) {
            tokenBank.removeToken(Token.GOLD, 1);
            player.getTokens().addToken(Token.GOLD, 1);
        }
    }

    public void reserveDevelopmentFromLevel(int level, Player player){
        if (playerTurnChecker(player)) {
            player.reserveDevelopment(market.takeTopDevelopment(level));
            givePlayerGoldTokenIfPossible(player);
            endTurn();
        }
    }

    public void checkForNoble(){
        List<Noble> possibleNobles = new ArrayList<>();
        for (Noble noble : unclaimedNobles){
            if (playerMeetsRequirements(currentPlayer, noble)){
                possibleNobles.add(noble);

            }

        }
        if (possibleNobles.isEmpty()){
            return;
        }
        chooseNobleNecessaryCheck(possibleNobles);
    }

    private void chooseNobleNecessaryCheck(List<Noble> possibleNobles){
        if (possibleNobles.size() > ONE_NOBLE){
            setGameState(GameState.CHOOSE_NOBLE);
        } else {
            acquireNoble(possibleNobles);
        }
    }

    public void chooseNoble(Noble noble) {
        if (!unclaimedNobles.contains(noble)) {
            throw new IllegalArgumentException("Noble not available");
        }
        if (!playerMeetsRequirements(currentPlayer, noble)) {
            throw new IllegalArgumentException("Player does not meet requirements for this noble");
        }

        currentPlayer.claimNoble(noble);
        unclaimedNobles.remove(noble);
        chosenNoble = noble;
    }


    public Noble getChosenNoble() {
        return chosenNoble;
    }


    private boolean playerMeetsRequirements(Player player, Noble noble) {
        for (Token bonus : Token.values()) {
            int required = noble.neededBonuses().getTokens().get(bonus);
            int actual = player.getBonuses().getTokens().get(bonus);
            if (actual < required) {
                return false;
            }
        }
        return true;
    }

    public void acquireNoble(List<Noble> possibleNobles){
        currentPlayer.claimNoble(possibleNobles.getFirst());
    }

    public void acquireTokens(Player player, Purse tokens){
        if(playerTurnChecker(player)){
            player.acquireTokens(tokens);
            tokenBank.removeTokens(tokens);
            endTurn();
        }
    }

    public boolean playerTurnChecker(Player player) {
        if(!player.equals(currentPlayer)){
            throw new IllegalStateException("It's not this player's turn");
        } else {
            return true;
        }
    }

    public void returnTokens(Player player, Purse tokensToReturn) {
        if (playerTurnChecker(player)){
            player.returnTokens(tokensToReturn);
            tokenBank.addTokens(tokensToReturn);
        }

    }

    public Set<Noble> setUnclaimedNobles() {
        int TOTAL_AMOUNT_UNCLAIMED_NOBLES = getTotalPlayers() + 1;

        List<Noble> allNobles = new ArrayList<>();
        Set<Noble> selectedNobles = new HashSet<>();

        try {
            File nobleData = new File("src/main/resources/data/nobles.txt");
            Scanner reader = new Scanner(nobleData);
            reader.nextLine(); //Skip first line because of headers

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] nobleInfo = data.split("\\t");

                String name = nobleInfo[0];
                char[] costChars = nobleInfo[1].toCharArray();
                int prestigePoints = Integer.parseInt(nobleInfo[2]);

                Purse costs = new Purse();
                for (char c : costChars) {
                    costs.addToken(Token.getTokenType(c), 1);
                }

                allNobles.add(new Noble(name, prestigePoints, costs));
            }

            Collections.shuffle((List<?>) allNobles);

            for (int i = 0; i < TOTAL_AMOUNT_UNCLAIMED_NOBLES + 1; i++) {
                selectedNobles.add(allNobles.get(i));
            }

            return selectedNobles;

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found.");
        }
    }

    //For testing purposes
    public void setTokenBank(Purse tokensToSetTokenBank){
        this.tokenBank = tokensToSetTokenBank;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Set<Noble> getUnclaimedNobles() {
        return unclaimedNobles;
    }

    public Purse getTokenBank() {
        return tokenBank;
    }

    private Purse generateTokenBank() {
        int amountPerToken = 0;

        if (getTotalPlayers() == 4) {
            amountPerToken = TOKEN_AMOUNT_IN_TOKENBANK_FOR_FOUR_PLAYERS;
        }
        else if (getTotalPlayers() == 3) {
            amountPerToken = TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS;
        }
        else if (getTotalPlayers() == 2) {
            amountPerToken = TOKEN_AMOUNT_IN_TOKENBANK_FOR_TWO_PLAYERS;
        }

        return generateTokenBank(amountPerToken);

    }

    private Purse generateTokenBank(int amountPerToken) {
        return new Purse(
                Map.of(
                        Token.DIAMOND, amountPerToken,
                        Token.EMERALD, amountPerToken,
                        Token.ONYX, amountPerToken,
                        Token.RUBY, amountPerToken,
                        Token.SAPPHIRE, amountPerToken,
                        Token.GOLD, INITIAL_GOLD_AMOUNT
                )
        );
    }
}
