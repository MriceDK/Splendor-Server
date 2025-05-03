package be.howest.ti.game.logic;

import java.util.List;

public class Level {

    private List<Development> visibleDevelopments;
    private List<Development> invisibleDevelopments;

    public Level(List<Development> developments){
        //TODO
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
