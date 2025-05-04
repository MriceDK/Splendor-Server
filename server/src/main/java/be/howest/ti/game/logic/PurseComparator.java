package be.howest.ti.game.logic;

import java.util.Comparator;

public class PurseComparator implements Comparator<Purse> {

    public int compare(Purse p1, Purse p2) {
        for (Token token : Token.values()){
            int count1 = p1.getTokens().get(token);
            int count2 = p2.getTokens().get(token);

            if (count1 != count2){
                return Integer.compare(count1, count2);

            }
        }
        return 0;
    }
}
