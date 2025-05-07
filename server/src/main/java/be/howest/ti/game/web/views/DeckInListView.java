package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Deck;

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

    // TODO getVisibleCards -> view van development

}
