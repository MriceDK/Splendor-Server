package be.howest.ti.game.logic;

public enum GameState {

    TURN_ACTION,
    RETURN_GEMS,
    CHOOSE_NOBLE,
    WINNER_FOUND;

    public String toDisplayName() {
        String[] words = name().split("_");

        for (int i = 0; i < words.length; i++) {
            words[i] = toFirstLetterUppercased(words[i]);
        }

        return String.join("", words);
    }

    private String toFirstLetterUppercased(String word) {
        return word.charAt(0) + word.substring(1).toLowerCase();
    }
}
