package be.howest.ti.game.logic;

import java.util.*;

public class Level {

    private static final int maxVisible = 4;
    private final List<Development> visibleDevelopments = new ArrayList<>(maxVisible);
    private final Queue<Development> invisibleDevelopments;
    private final int levelNumber;

    public Level(List<Development> developments, int levelNumber) {
        for (Development development : developments) {
            if (development.level() != levelNumber) {
                throw new IllegalArgumentException(development.name() + " does not match the level number of " + levelNumber);
            }
        }

        this.levelNumber = levelNumber;
        this.invisibleDevelopments = new LinkedList<>(developments);
//        TODO: shuffle the developments or just insert already shuffled developments
//        Collections.shuffle((List<?>) invisibleDevelopments);
        for (int i = 0; i < maxVisible && i < developments.size(); i++) {
            this.visibleDevelopments.add(takeTopDevelopment());
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Development> getVisibleDevelopments() {
        return Collections.unmodifiableList(visibleDevelopments);
    }

    public void makeVisible(Development development){
        if (!invisibleDevelopments.contains(development)) {
            throw new IllegalArgumentException(development.name() + " was not found in invisible developments");
        }
        this.visibleDevelopments.add(development);
        this.invisibleDevelopments.remove(development);
    }



    public void removeVisibleDevelopment(Development development){
        if (visibleDevelopments.contains(development)) {
            visibleDevelopments.remove(development);
            makeVisible(invisibleDevelopments.peek());
        } else throw new IllegalArgumentException("Development not found in visible developments");
    }

    public int getTotalInvisible(){
        return this.invisibleDevelopments.size();
    }

    public Development takeTopDevelopment(){
        if (invisibleDevelopments.isEmpty()) {
            throw new IllegalStateException("No developments left in this level");
        }
        Development firstDevelopment = invisibleDevelopments.peek();
        invisibleDevelopments.remove(firstDevelopment);
        return firstDevelopment;
    }

    @Override
    public String toString() {
        return "Level{" +
                "invisibleDevelopments=" + invisibleDevelopments.size() +
                "visibleDevelopments=" + visibleDevelopments +
                '}';
    }
}
