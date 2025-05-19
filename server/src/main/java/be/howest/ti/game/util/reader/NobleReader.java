package be.howest.ti.game.util.reader;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class NobleReader {


    private final List<Noble> nobles;

    public NobleReader() {
        List<Noble> allNobles = new ArrayList<>();


        InputStream in = this.getClass().getResourceAsStream("/data/nobles.txt");

        Scanner reader = new Scanner(in);
        reader.nextLine(); //Skip first line because of headers

        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] nobleInfo = data.split("\\t");

            String name = nobleInfo[0];
            char[] costChars = nobleInfo[1].toCharArray();
            int prestigePoints = Integer.parseInt(nobleInfo[2]);

            Purse costs = new Purse();
            for (char c : costChars) {
                costs.addToken(Token.getTokenType(c), 1);
            }

            allNobles.add(new Noble(name, prestigePoints, costs));
        }

        nobles = allNobles;

    }

    public Set<Noble> getRandomNobles(int playerCount) {
        int TOTAL_AMOUNT_UNCLAIMED_NOBLES = playerCount + 1;

        List<Noble> allNobles = new ArrayList<>(nobles);
        Set<Noble> selectedNobles = new HashSet<>();

        Collections.shuffle((List<?>) allNobles);

        for (int i = 0; i < TOTAL_AMOUNT_UNCLAIMED_NOBLES + 1; i++) {
            selectedNobles.add(allNobles.get(i));
        }
        return selectedNobles;
    }

    public List<Noble> getAllNobles() {
        return nobles;
    }



}
