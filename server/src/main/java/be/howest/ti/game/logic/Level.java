package be.howest.ti.game.logic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {

    private static final int maxVisible = 4;
    private final List<Development> visibleDevelopments = new ArrayList<>(maxVisible);
    private final List<Development> invisibleDevelopments;

    public Level(List<Development> developments){
        this.invisibleDevelopments = developments;
        for (int i = 0; i < maxVisible; i++) {
            makeRandomVisible();
        }
    }

    public void makeRandomVisible() {
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(invisibleDevelopments.size());
        makeVisible(invisibleDevelopments.get(randomInt));
    }

    private void makeVisible(Development development){
        if (!invisibleDevelopments.contains(development)) {
            throw new IllegalArgumentException("Development not found in invisible developments");
        }
        this.visibleDevelopments.add(development);
        this.invisibleDevelopments.remove(development);
    }

    public List<Development> getVisibleDevelopments() {
        if (visibleDevelopments.isEmpty()) {
            throw new IllegalStateException("No visible developments");
        }
        return Collections.unmodifiableList(visibleDevelopments);
    }

    public void removeDevelopment(Development development){
        if (visibleDevelopments.contains(development)) {
            visibleDevelopments.remove(development);
        } else throw new IllegalArgumentException("Development not found in visible developments");

    }

    public int getTotalInvisible(){
        return this.invisibleDevelopments.size();
    }

    public Development takeTopDevelopment(){
        if (invisibleDevelopments.isEmpty()) {
            throw new IllegalStateException("No invisible developments left");
        }
        Development firstDevelopment = invisibleDevelopments.getFirst();
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
