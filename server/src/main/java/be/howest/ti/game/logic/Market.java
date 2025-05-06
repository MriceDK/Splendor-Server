package be.howest.ti.game.logic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market {

    private final Map<Integer, Level> levels;

    public Market(){
        this.levels = new HashMap<>();
        // TODO: Make the developments actually real and not just placeholders
        levels.put(1, new Level(List.of(
                new Development("Development 1", 1, 1, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 2", 1, 2, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 3", 1, 3, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 4", 1, 4, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 5", 1, 5, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 6", 1, 6, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1)))
        ), 1));
        levels.put(2, new Level(List.of(
                new Development("Development 7", 2, 1, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 8", 2, 2, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 9", 2, 3, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 10", 2, 4, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 11", 2, 5, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 12", 2, 6, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1)))
        ), 2));
        levels.put(3, new Level(List.of(
                new Development("Development 13", 3, 1, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 14", 3, 2, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 15", 3, 3, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 16", 3, 4, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 17", 3, 5, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1))),
                new Development("Development 18", 3, 6, Token.EMERALD, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1)))
        ), 3));
    }

    public Map<Integer, Level> getLevels() {
        return levels;
    }

    public void makeVisibleLevel(int level, Development development){
        levels.get(level).makeVisible(development);
    }

    public List<Development> getVisibleDevelopments(int level){
        return levels.get(level).getVisibleDevelopments();
    }

    public int getTotalInvisibleDevelopments(int level){
        return levels.get(level).getTotalInvisible();
    }

    public Development takeTopDevelopment(int level){
        return levels.get(level).takeTopDevelopment();
    }

    public Development removeVisibleDevelopment(String developmentName) {
        Development matchingDevelopment = findMatchingDevelopmentOverAllLevels(developmentName);
        return levels.get(matchingDevelopment.level()).removeVisibleDevelopment(developmentName);
    }

    public Development findMatchingDevelopmentOverAllLevels(String developmentName) {
        Development matchingDevelopment = null;
        for (Level level : levels.values()) {
            if (level.getVisibleDevelopments().contains(level.findMatchingDevelopment(developmentName)) && matchingDevelopment == null) {
                matchingDevelopment = level.findMatchingDevelopment(developmentName);
            }
        }
        if (matchingDevelopment == null) {
            throw new IllegalStateException("Development not found in visible developments");
        }
        return matchingDevelopment;
    }
}
