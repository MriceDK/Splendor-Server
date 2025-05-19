package be.howest.ti.game.util.reader;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DevelopmentReader {

    private final List<List<Development>> levels = new ArrayList<>();

    public DevelopmentReader() {
        List<Development> developmentLevel1 = new ArrayList<>();
        List<Development> developmentLevel2 = new ArrayList<>();
        List<Development> developmentLevel3 = new ArrayList<>();

        //File developmentCards = new File("src/main/resources/data/developments.txt");
        InputStream in = this.getClass().getResourceAsStream("/data/developments.txt");
        Scanner reader = new Scanner(in);
        reader.nextLine(); //Skip first line because of headers

        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] developmentInfo = data.split("\\t");

            String name = developmentInfo[0];
            int level = Integer.parseInt(developmentInfo[1]);
            Token bonus = Token.getTokenType(developmentInfo[2].toCharArray()[0]);
            int prestigePoints = Integer.parseInt(developmentInfo[3]);
            char[] costChars = developmentInfo[5].toCharArray();

            Purse costs = new Purse();
            for (char c : costChars) {
                costs.addToken(Token.getTokenType(c), 1);
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

        levels.add(developmentLevel1);
        levels.add(developmentLevel2);
        levels.add(developmentLevel3);

    }

    public List<Development> getFirstLevel() {
        return levels.getFirst();
    }

    public List<Development> getSecondLevel() {
        return levels.get(1);
    }

    public List<Development> getThirdLevel() {
        return levels.get(2);
    }

    public List<Development> getAllDevelopments() {
        List<Development> developments = new ArrayList<>();
        developments.addAll(levels.getFirst());
        developments.addAll(levels.get(1));
        developments.addAll(levels.get(2));
        return developments;
    }
}
