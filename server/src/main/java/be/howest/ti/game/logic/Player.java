package be.howest.ti.game.logic;

import java.util.*;

public class Player {

    private final String name;
    private Purse tokens;
    private Purse bonuses;
    private Set<Noble> acquiredNobles; // TODO make final
    private int prestigePoints;
    private final List<Development> reservedDevelopments;

    private static final int MAX_TOTAL_TOKENS_PER_PLAYER = 10;

    private static final int ZERO_TOKENS = 0;
    private static final int MAX_DIFFERENT_TOKENS = 3;
    private static final int MAX_OF_SAME_TOKEN = 2;
    private static final int SAME_TOKEN = 1;

    public Player (String name){
        this.name = name;
        this.reservedDevelopments = new ArrayList<>();
        this.tokens = new Purse();
        this.bonuses = new Purse();
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

    public void checkIfPaymentIsSufficient(Development development, Purse payment){
        for (Token token : development.cost().getTokens().keySet()) {
            int ownTokenValue = tokens.getTokens().get(token);
            int paymentTokenValue = payment.getTokens().get(token);
            int bonusTokenValue = bonuses.getTokens().get(token);
            int developmentTokenCost = development.cost().getTokens().get(token);

            if (paymentTokenValue + bonusTokenValue != developmentTokenCost) throw new IllegalArgumentException("The payment is not sufficient");
            if (ownTokenValue < paymentTokenValue) throw new IllegalArgumentException("You don't have enough tokens of this type");
        }
    }

    public void checkIfPlayerIsAllowedToReserve() {
        if (reservedDevelopments.size() == 3) {
            throw new IllegalStateException("You can only have 3 reserved cards at a time");
        }
    }

    public void buyDevelopment(Development development, Purse payment){
        checkIfPaymentIsSufficient(development, payment);
        prestigePoints += development.prestigePoints();
        bonuses.addToken(development.bonus(), 1);
        tokens.removeTokens(payment.getTokens());
    }

    public void reserveDevelopment(Development development){
        checkIfPlayerIsAllowedToReserve();
        reservedDevelopments.add(development);
    }

    public void claimNoble(Noble noble){acquiredNobles.add(noble);}

    public void acquireTokens(Map<Token,Integer> tokensToAcquire){
        int sizeOfTokensToAcquire = 0;
        for (Map.Entry<Token, Integer> tokenToAcquire : tokensToAcquire.entrySet()){
            if (tokenToAcquire.getValue() > ZERO_TOKENS){
                sizeOfTokensToAcquire++;
            }


        }
        ruleCheckToAcquireTokens(tokensToAcquire, sizeOfTokensToAcquire);
        tokens.addTokens(tokensToAcquire);
    }

    private void ruleCheckToAcquireTokens(Map<Token, Integer> tokensToAcquire, int sizeOfTokensToAcquire) {
        if (sizeOfTokensToAcquire > MAX_DIFFERENT_TOKENS){
            throw new IllegalArgumentException("You cannot acquire more than three different types of tokens at the same time");
        }

        for (Map.Entry<Token, Integer> tokenToAdd : tokensToAcquire.entrySet()){
            if (tokenToAdd.getValue() > MAX_OF_SAME_TOKEN){
                throw new IllegalArgumentException("You cannot acquire more than two tokens of the same type");
            }

            if (tokenToAdd.getValue() == MAX_OF_SAME_TOKEN && sizeOfTokensToAcquire != SAME_TOKEN) {
                throw new IllegalArgumentException("You can only take two of the same token type if you're taking only that type");
            }

        }
    }


    public void totalReturnTokens(Map<Token, Integer> tokensToReturn) {
        int totalTokens = tokens.getTotal();
        int returnTokens = tokensToReturn.values().stream().mapToInt(Integer::intValue).sum(); //get values out of map, make them int and adds them up
        int diffTotalTokensAndReturnTokens = totalTokens - returnTokens;

        if (totalTokens <= MAX_TOTAL_TOKENS_PER_PLAYER) {
            throw new IllegalStateException("You may only return tokens if you have more than 10.");
        }
        if (diffTotalTokensAndReturnTokens > MAX_TOTAL_TOKENS_PER_PLAYER) {
            throw new IllegalArgumentException("Returned tokens are insufficient. You must return enough to have 10.");
        }

        if (diffTotalTokensAndReturnTokens < MAX_TOTAL_TOKENS_PER_PLAYER) {
            throw new IllegalArgumentException("Returned tokens are insufficient. You must return enough to have 10.");
        }

        tokens.removeTokens(tokensToReturn);
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
}
