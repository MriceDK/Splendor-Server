package be.howest.ti.game.logic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market {

    private final Map<Integer, Deck> levels;

    public Market(){
        this.levels = new HashMap<>();
        // TODO: Make the developments actually real and not just placeholders
        levels.put(1, new Deck(List.of(
                new Development("Diamond Mine", 1, 0, Token.DIAMOND, new Purse(Map.of(Token.ONYX, 1, Token.SAPPHIRE, 1, Token.DIAMOND, 3))),
                new Development("Diamond Vein", 1, 0, Token.DIAMOND, new Purse(Map.of(Token.ONYX, 2, Token.SAPPHIRE, 2))),
                new Development("Shimmering Quarry", 1, 0, Token.DIAMOND, new Purse(Map.of(Token.SAPPHIRE, 3))),
                new Development("Brilliant Mine", 1, 0, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 2, Token.ONYX, 1))),
                new Development("Radiant Crystal Cave", 1, 0, Token.DIAMOND, new Purse(Map.of(Token.ONYX, 1, Token.SAPPHIRE, 2, Token.EMERALD, 2))),
                new Development("Facet Workshop", 1, 0, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 1, Token.ONYX, 1, Token.SAPPHIRE, 1, Token.EMERALD, 2))),
                new Development("Glistening Vault", 1, 0, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 1, Token.ONYX, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1))),
                new Development("Sparkling Chamber", 1, 1, Token.DIAMOND, new Purse(Map.of(Token.EMERALD, 4)))
        ), 1));
        levels.put(2, new Deck(List.of(
                new Development("Polished Vault", 2, 1, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 2, Token.ONYX, 2, Token.EMERALD, 3))),
                new Development("Diamond Refinery", 2, 1, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 3, Token.SAPPHIRE, 3, Token.DIAMOND, 2))),
                new Development("Radiant Workshop", 2, 2, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 4, Token.ONYX, 2, Token.EMERALD, 1))),
                new Development("Gleaming Estate", 2, 2, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 5))),
                new Development("Sparkling Guild", 2, 2, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 5, Token.ONYX, 3))),
                new Development("Brilliant Collection", 2, 3, Token.DIAMOND, new Purse(Map.of(Token.DIAMOND, 6)))


                ), 2));
        levels.put(3, new Deck(List.of(
                new Development("Grand Diamond Vault", 3, 3, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 5, Token.ONYX, 3, Token.SAPPHIRE, 3, Token.EMERALD, 3))),
                new Development("Exquisite Diamond Vault", 3, 4, Token.DIAMOND, new Purse(Map.of(Token.ONYX, 7))),
                new Development("Royal Diamond Chamber", 3, 4, Token.DIAMOND, new Purse(Map.of(Token.RUBY, 3, Token.ONYX, 6, Token.DIAMOND, 3))),
                new Development("Master Diamond Atelier", 3, 5, Token.DIAMOND, new Purse(Map.of(Token.ONYX, 7, Token.DIAMOND, 3))),


                ), 3));
    }

    public Map<Integer, Deck> getLevels() {
        return levels;
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
        for (Deck level : levels.values()) {
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
