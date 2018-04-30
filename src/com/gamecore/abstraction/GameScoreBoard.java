package com.gamecore.abstraction;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;

public abstract class GameScoreBoard {

    public static HashMap<Player, GameScoreBoard> userScoreboards = new HashMap<>(); /** 유저 스코어 보드 HashMap */
    public static HashMap<GameRoom, GameScoreBoard> roomSocreBoards = new HashMap<>(); /** 게임 대기실 스코어보드 HashMap */
    public static HashMap<Game, GameScoreBoard> gameScoreBoards = new HashMap<>(); /** 게임 스코어보드 HashMap */

    protected Player player;
    protected GameRoom room;
    protected Game game;
    protected ScoreboardManager manager;
    protected Scoreboard scoreboard;
    protected Objective objective;

    protected GameScoreBoard(Player player){
        this.player = player;
        userScoreboards.put(player, this);
    }

    protected GameScoreBoard(GameRoom room){
        this.room = room;
        roomSocreBoards.put(room, this);
    }

    protected GameScoreBoard(Game game){
        this.game = game;
        gameScoreBoards.put(game, this);
    }

    public abstract void floatBoard(); /** 스코어보드를 플레이어에게 띄웁니다. */
    public abstract void delete(); /** 띄우던 스코어보드를 삭제합니다. */

}
