package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.util.reader.DevelopmentReader;
import be.howest.ti.game.util.reader.NobleReader;

import java.util.List;

public abstract class GameOperations {
    public static List<Development> getAllDevelopments() {
        DevelopmentReader reader = new DevelopmentReader();
        return reader.getAllDevelopments();
    }

    public static List<Noble> getAllNobles() {
        NobleReader reader = new NobleReader();
        return reader.getAllNobles();
    }
}
