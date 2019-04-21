package indi.wiio.info;

import indi.wiio.info.character.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static String path = "./user/games.ser";
    private static String mainGame;

    private static Map<String, Game> gameMap = new HashMap<>();

    public Game getGame(){
        return getGame(mainGame);
    }

    public Game getGame(String gameName){
        return gameMap.getOrDefault(gameName, null);
    }

    private Resources resources = Resources.getResources();
    private Map<String, Others> othersMap = Others.getOthersMap();
    private Self self = Self.getSelf();
    private List<ChatMessage> chatMessages = new ArrayList<>();

    //TODO: 怎么实现保存机制比较好？？

}
