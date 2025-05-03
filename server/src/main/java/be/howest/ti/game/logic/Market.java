package be.howest.ti.game.logic;
import java.util.List;
import java.util.Map;

public class Market {

    private Map<String, Level> levels; // TODO make final

    public Market(){
        //TODO
    }

    public Map<String, Level> getLevels() {
        return levels;
    }

    public void makeVisibleLevel(String level){
        //TODO
    }

    public List<Development> getVisibleDevelopments(String level){
        //TODO
        return null;
    }

    public int getTotalInvisibleDevelopments(String level){
        //TODO
        return -1;
    }

    public Development takeTopDevelopment(String level){
        //TODO
        return null;
    }

    public void removeDevelopment(String level, Development development){
        //TODO
    }
}
