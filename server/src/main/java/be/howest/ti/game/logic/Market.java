package be.howest.ti.game.logic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market {

    private Map<String, Level> levels; // TODO make final

    public Market(){
        this.levels = new HashMap<>();
        // TODO: Make the developments actually real and not just placeholders
        levels.put("1", new Level(List.of(
                new Development("Development 1", 1, 1, null, null),
                new Development("Development 2", 1, 2, null, null),
                new Development("Development 3", 1, 3, null, null),
                new Development("Development 4", 1, 4, null, null)
        ), 1));
        levels.put("2", new Level(List.of(
                new Development("Development 5", 2, 1, null, null),
                new Development("Development 6", 2, 2, null, null),
                new Development("Development 7", 2, 3, null, null),
                new Development("Development 8", 2, 4, null, null)
        ), 2));
        levels.put("3", new Level(List.of(
                new Development("Development 9", 3, 1, null, null),
                new Development("Development 10", 3, 2, null, null),
                new Development("Development 11", 3, 3, null, null),
                new Development("Development 12", 3, 4, null, null)
        ), 3));
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
