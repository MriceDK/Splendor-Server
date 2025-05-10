package be.howest.ti.game.logic;
import java.util.*;
import java.io.*;

public class Market {

    private final Map<Integer, Deck> levels;

    public Market(){
        this.levels = new HashMap<>();
        fillMarket();
    }

    private void fillMarket() {
        List<Development> developmentLevel1 = new ArrayList<>();
        List<Development> developmentLevel2 = new ArrayList<>();
        List<Development> developmentLevel3 = new ArrayList<>();

        try {
            File developmentCards = new File("src/main/resources/data/developments.txt");
            Scanner reader = new Scanner(developmentCards);
            reader.nextLine(); //Skip first line because of headers

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] developmentInfo = data.split("\\t");

                String name = developmentInfo[0];
                int level = Integer.parseInt(developmentInfo[1]);
                Token bonus = getTokenType(developmentInfo[2].toCharArray()[0]);
                int prestigePoints = Integer.parseInt(developmentInfo[3]);
                char[] costChars = developmentInfo[5].toCharArray();

                Purse costs = new Purse();
                for (char c : costChars) {
                    costs.addToken(getTokenType(c), 1);
                }

                Development card = new Development(name, level, prestigePoints, bonus, costs);

                if (level == 1) {
                    developmentLevel1.add(card);
                } else if (level == 2) {
                    developmentLevel2.add(card);
                } else if (level == 3) {
                    developmentLevel3.add(card);
                } else {
                    throw new IllegalStateException("Unknown level: " + level);
                }
            }
            reader.close();

            levels.put(1, new Deck(developmentLevel1, 1));
            levels.put(2, new Deck(developmentLevel2, 2));
            levels.put(3, new Deck(developmentLevel3, 3));

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found.");
        }
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

    //TODO: Move/Delete this method because it is exactly the same as in SplendorGame class
    public Token getTokenType(char tokenChar) {
        return switch (tokenChar) {
            case 'C' -> Token.DIAMOND;
            case 'S' -> Token.SAPPHIRE;
            case 'R' -> Token.RUBY;
            case 'E' -> Token.EMERALD;
            case 'O' -> Token.ONYX;
            default -> throw new IllegalArgumentException("Invalid token char: " + tokenChar);
        };
    }
}
