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
        //TODO
    }

    public int getTotalInvisible(){
        //TODO
        return -1;
    }

    public Development takeTopDevelopment(){
        //TODO
        return null;
    }
}
