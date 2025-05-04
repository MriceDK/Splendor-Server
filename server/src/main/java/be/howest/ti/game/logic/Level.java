package be.howest.ti.game.logic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private List<Development> visibleDevelopments = new ArrayList<>();
    private final List<Development> invisibleDevelopments;

    public Level(List<Development> developments){
        this.invisibleDevelopments = developments;

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 4; i++) {
            int randomInt = secureRandom.nextInt(invisibleDevelopments.size());
            makeVisible(invisibleDevelopments.get(randomInt));
        }
    }

    public List<Development> getVisibleDevelopments() {
        return visibleDevelopments;
    }

    public void removeDevelopment(Development development){
        //TODO
    }

    public void makeVisible(Development development){
        this.visibleDevelopments.add(development);
        this.invisibleDevelopments.remove(development);
    }

    public int getTotalInvisible(){
        return this.invisibleDevelopments.size();
    }

    public Development takeTopDevelopment(){
        Development firstDevelopment = invisibleDevelopments.getFirst();
        invisibleDevelopments.remove(firstDevelopment);
        return firstDevelopment;
    }
}
