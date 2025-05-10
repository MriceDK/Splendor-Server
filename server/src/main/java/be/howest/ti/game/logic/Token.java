package be.howest.ti.game.logic;

public enum Token { // TODO volgorde van tokens in de list moet nog geïmplementeerd worden
    DIAMOND {
        @Override
        public String toString() {
            return "Diamond";
        }
    },

    RUBY {
        @Override
        public String toString() {
            return "Ruby";
        }
    },

    SAPPHIRE {
        @Override
        public String toString() {
            return "Sapphire";
        }
    },

    ONYX {
        @Override
        public String toString() {
            return "Onyx";
        }
    },

    EMERALD {
        @Override
        public String toString() {
            return "Emerald";
        }
    },

    GOLD {
        @Override
        public String toString() {
            return "Gold";
        }
    }

}
