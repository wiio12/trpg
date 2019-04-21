package indi.wiio.info;

import indi.wiio.info.character.Resources;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private static String path = "./user/games.ser";

    private static Map<String, Game> gameMap = new HashMap<>();

    private Resources resources;
    private Map<String, Others> othersMap;
    private Self self;



}
