package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;

import java.util.*;

public class Deck {

    private static final int MAX_VISIBLE = 4;
    private final List<Development> visibleDevelopments = new ArrayList<>(MAX_VISIBLE);
    private final Queue<Development> invisibleDevelopments;
    private final int levelNumber;

    public Deck(List<Development> developments, int levelNumber) {
        for (Development development : developments) {
            if (development.level() != levelNumber) {
                throw new IllegalArgumentException(development.name() + " does not match the level number of " + levelNumber);
            }
        }

        this.levelNumber = levelNumber;
        this.invisibleDevelopments = new LinkedList<>(developments);

        Collections.shuffle((List<?>) invisibleDevelopments);
        for (int i = 0; i < MAX_VISIBLE && i < developments.size(); i++) {
            makeVisible(takeTopDevelopment());
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Development> getVisibleDevelopments() {
        return Collections.unmodifiableList(visibleDevelopments);
    }

    private void makeVisible(Development development) {
        this.visibleDevelopments.add(development);
    }


    public void removeVisibleDevelopment(Development development) {
        if (visibleDevelopments.contains(development)) {
            visibleDevelopments.remove(development);
        } else throw new SplendorGameResourceNotFoundException("Development not found in visible developments");
    }

    public void refillVisibleDevelopments() {
        makeVisible(takeTopDevelopment());
    }

    public Development findMatchingDevelopment(String developmentName) {
        for (Development development : visibleDevelopments) {
            if (development.name().equals(developmentName)) {
                return development;
            }
        }
        return null;
    }

    public int getTotalInvisible() {
        return this.invisibleDevelopments.size();
    }

    public Development takeTopDevelopment() {
        if (invisibleDevelopments.isEmpty()) {
            throw new SplendorGameResourceNotFoundException("No developments left in this level");
        }
        return invisibleDevelopments.poll();
    }
}
