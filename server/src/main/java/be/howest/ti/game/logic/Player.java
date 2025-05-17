package be.howest.ti.game.logic;

import java.util.*;

public class Player {

    private final String name;
    private Purse tokens;
    private Purse bonuses;
    private Set<Noble> acquiredNobles; // TODO make final
    private int prestigePoints;
    private final List<Development> reservedDevelopments;
    private final List<Development> ownedDevelopments;

    private static final int TOO_MANY_TOTAL_TOKENS_PER_PLAYER = 11;
    private static final int MAX_TOTAL_TOKENS_PER_PLAYER = 10;
    private static final int ZERO_TOKENS = 0;
    private static final int MAX_DIFFERENT_TOKENS = 3;
    private static final int MAX_OF_SAME_TOKEN = 2;

    private static final int MIN_POINTS_NEEDED_TO_WIN = 15;

    public Player (String name){
        this.name = name;
        this.acquiredNobles = new HashSet<>();
        this.reservedDevelopments = new ArrayList<>();
        this.tokens = new Purse();
        this.bonuses = new Purse();
        this.prestigePoints = 0;
        this.ownedDevelopments = new ArrayList<>();
    }

    //for testing purposes
    public void setTokens(Purse purse){
        this.tokens = purse;
    }

    public String getName() {
        return name;
    }

    public Purse getTokens() {
        return tokens;
    }

    public Purse getBonuses() {
        return bonuses;
    }

    public Set<Noble> getAcquiredNobles() {
        return acquiredNobles;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public List<Development> getReservedDevelopments() {
        return reservedDevelopments;
    }

    public List<Development> getOwnedDevelopments() {
        return ownedDevelopments;
    }

    public void checkIfPaymentIsSufficient(Development development, Purse payment) {
        int goldNeeded = getNeededGold(development.cost(), payment);

        if (goldNeeded != 0 && !hasEnoughGoldTokens(goldNeeded, payment)) {
            throw new IllegalArgumentException("The payment is not sufficient");
        }
    }

    private boolean hasEnoughGoldTokens(int goldNeeded, Purse payment) {
        return payment.getTokenValue(Token.GOLD) == goldNeeded;
    }

    private int getNeededGold(Purse cost, Purse payment) {
        int totalStillNeeded = 0;

        for (Token token : cost.getNormalTokens().keySet()) {

            int tokenValueNeeded = cost.getTokenValue(token);
            int bonusValue = bonuses.getTokenValue(token);
            int tokenValue = payment.getTokenValue(token);

            int totalTokenWorth = bonusValue + tokenValue;

            if (bonusValue >= tokenValueNeeded && tokenValue > 0) {
                throw new IllegalArgumentException("The payment is not sufficient");
            }

            if (bonusValue < tokenValueNeeded && totalTokenWorth != tokenValueNeeded) {
                totalStillNeeded += tokenValueNeeded - totalTokenWorth;
            }
        }

        return totalStillNeeded;
    }


    public void checkIfPlayerIsAllowedToReserve() {
        if (reservedDevelopments.size() == 3) {
            throw new IllegalStateException("You can only have 3 reserved cards at a time");
        }
    }

    public void buyDevelopment(Development development, Purse payment){
        checkIfPaymentIsSufficient(development, payment);
        tokens.removeTokens(payment);
        ownedDevelopments.add(development);
        prestigePoints += development.prestigePoints();
        bonuses.addToken(development.bonus(), 1);
    }

    public void reserveDevelopment(Development development){
        checkIfPlayerIsAllowedToReserve();
        reservedDevelopments.add(development);
    }

    public void claimNoble(Noble noble){acquiredNobles.add(noble);}

    public void acquireTokens(Purse tokensToAcquire){
        int sizeOfTokensToAcquire = 0;
        for (Map.Entry<Token, Integer> tokenToAcquire : tokensToAcquire.getTokens().entrySet()){
            if (tokenToAcquire.getValue() > ZERO_TOKENS){
                sizeOfTokensToAcquire++;
            }


        }
        ruleCheckToAcquireTokens(tokensToAcquire, sizeOfTokensToAcquire);
        tokens.addTokens(tokensToAcquire);
    }

    private void ruleCheckToAcquireTokens(Purse tokensToAcquire, int sizeOfTokensToAcquire) {
        if (sizeOfTokensToAcquire > MAX_DIFFERENT_TOKENS){
            throw new IllegalArgumentException("You cannot acquire more than three different types of tokens at the same time");
        }

        for (Map.Entry<Token, Integer> tokenToAdd : tokensToAcquire.getTokens().entrySet()){
            if (tokenToAdd.getValue() > MAX_OF_SAME_TOKEN){
                throw new IllegalArgumentException("You cannot acquire more than two tokens of the same type");
            }

            if (tokenToAdd.getValue() == MAX_OF_SAME_TOKEN && sizeOfTokensToAcquire != 1) {
                throw new IllegalArgumentException("You can only take two of the same token type if you're taking only that type");
            }

        }
    }


    public void returnTokens(Purse totalReturnTokens) {
        int totalTokens = tokens.getTotal();
        int returnTokens = totalReturnTokens.getTokens().values().stream().mapToInt(Integer::intValue).sum(); //get values out of map, make them int and adds them up
        int diffTotalTokensAndReturnTokens = totalTokens - returnTokens;

        if (totalTokens <= MAX_TOTAL_TOKENS_PER_PLAYER) {
            throw new IllegalStateException("You may only return tokens if you have more than 10.");
        }
        if (diffTotalTokensAndReturnTokens >= TOO_MANY_TOTAL_TOKENS_PER_PLAYER) {
            throw new IllegalArgumentException("Returned tokens are insufficient. You must return enough to have 10.");
        }

        if (diffTotalTokensAndReturnTokens < MAX_TOTAL_TOKENS_PER_PLAYER) {
            throw new IllegalArgumentException("Returned tokens are insufficient. You must return enough to have 10.");
        }

        tokens.removeTokens(totalReturnTokens);
    }

    public boolean isWinnerWorthy() {
        return prestigePoints >= MIN_POINTS_NEEDED_TO_WIN;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    //For testing purposes
    public void setBonuses(Purse bonuses) {
        this.bonuses = bonuses;
    }

   //For testing purposes
    public void setAcquiredNobles(Set<Noble> nobles) {
        this.acquiredNobles = nobles;
    }

    public boolean hasTooManyTokens() {
        return tokens.getTotal() >= MAX_TOTAL_TOKENS_PER_PLAYER;
    }
}
