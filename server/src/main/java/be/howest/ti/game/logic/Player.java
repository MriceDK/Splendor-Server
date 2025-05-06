package be.howest.ti.game.logic;

import java.util.*;

public class Player {

    private final String name;
    private final Purse tokens;
    private Purse bonuses;
    private Set<Noble> acquiredNobles; // TODO make final
    private int prestigePoints;
    private final List<Development> reservedDevelopments;
    private final List<Development> boughtDevelopments;

    public Player (String name){
        this.name = name;
        this.reservedDevelopments = new ArrayList<>();
        this.boughtDevelopments = new ArrayList<>();
        this.tokens = new Purse();
        this.bonuses = new Purse();
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

    public List<Development> getBoughtDevelopments() {
        return boughtDevelopments;
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
        boughtDevelopments.add(development);
    }

    public void reserveDevelopment(Development development){
        checkIfPlayerIsAllowedToReserve();
        reservedDevelopments.add(development);
    }

    public void claimNoble(Noble noble){acquiredNobles.add(noble);}

    public void acquireTokens(Map<Token,Integer> tokensToAcquire){
        //TODO
    }
    public void returnTokens(Map<Token,Integer> tokens){
        //TODO
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
