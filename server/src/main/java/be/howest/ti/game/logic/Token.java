package be.howest.ti.game.logic;

public enum Token { // TODO volgorde van tokens in de list moet nog geïmplementeerd worden
    DIAMOND,

    RUBY,

    SAPPHIRE,

    ONYX,

    EMERALD,

    GOLD;


    public static Token getTokenType(char tokenChar) {
        return switch (tokenChar) {
            case 'C' -> Token.DIAMOND;
            case 'S' -> Token.SAPPHIRE;
            case 'R' -> Token.RUBY;
            case 'E' -> Token.EMERALD;
            case 'O' -> Token.ONYX;
            default -> throw new IllegalArgumentException("Invalid token char: " + tokenChar);
        };
    }

    public String toDisplayName(){
        String tokenLowerCase = name().toLowerCase();
        return Character.toUpperCase(tokenLowerCase.charAt(0)) + tokenLowerCase.substring(1);
    }
}


