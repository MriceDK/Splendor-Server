package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Deck;
import be.howest.ti.game.logic.Development;

import java.util.ArrayList;
import java.util.List;

public class DeckInListView {

    private final Deck deck;

    public DeckInListView(Deck deck) {
        this.deck = deck;
    }

    public int getLevel() {
        return deck.getLevelNumber();
    }

    public int getCardStackSize() {
        return deck.getTotalInvisible();
    }

    public List<DevelopmentInListView> getVisibleCards() {
        List<DevelopmentInListView> res = new ArrayList<>();

        for (Development visibleDevelopment : deck.getVisibleDevelopments()) {
            res.add(new DevelopmentInListView(visibleDevelopment));
        }

        return res;
    }

}
