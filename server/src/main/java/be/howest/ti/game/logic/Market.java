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

    public void makeVisibleLevel(String level, Development development){
        levels.get(level).makeVisible(development);
    }

    public List<Development> getVisibleDevelopments(String level){
        return levels.get(level).getVisibleDevelopments();
    }

    public int getTotalInvisibleDevelopments(String level){
        //TODO
        return levels.get(level).getTotalInvisible();
    }

    public Development takeTopDevelopment(String level){
        //TODO
        return levels.get(level).takeTopDevelopment();
    }

    public void removeDevelopment(String level, Development development){
        levels.get(level).removeVisibleDevelopment(development);
    }
}
