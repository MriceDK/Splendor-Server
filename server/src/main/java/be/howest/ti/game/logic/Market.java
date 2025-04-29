package be.howest.ti.game.logic;
import java.util.List;
import java.util.Map;

public class Market {

    private final Map<String, Level> levels;

    public Map<String, Level> getLevels() {
        return levels;
    }

    public void makeVisibleLevel(String level){
        //TODO
    }

    public List<Development> getVisibleDevelopments(String level){
        //TODO
    }

    public int getTotalInvisibleDevelopments(String level){
        //TODO
    }

    public Development takeTopDevelopment(String level){
        //TODO
    }

    public void removeDevelopment(String level, Development development){
        //TODO
    }
}
