package com.gamecore.defaultfunctions;

import com.gamecore.abstraction.GameRoom;
import com.gamecore.abstraction.GameScoreBoard;
import com.gamecore.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;

/**
 * Created by user on 2017-12-25.
 */
public class WaitRoomScoreBoard extends GameScoreBoard {

    public WaitRoomScoreBoard(GameRoom room) {
        super(room);
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective(room.getName(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(room.getGame().getCustomName());
    }

    @Override
    public void floatBoard() {
        this.delete();
        super.objective = super.scoreboard.registerNewObjective(this.room.getName(), "dummy");
        super.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        super.objective.setDisplayName(room.getGame().getCustomName());

        objective.getScore(Bukkit.getOfflinePlayer(" ")).setScore(10);
        objective.getScore(Bukkit.getOfflinePlayer("상태: " + room.getGame().getRunningType().toString())).setScore(9);
        objective.getScore(Bukkit.getOfflinePlayer("인원: " + room.getWatingUsersAmount() + "/" + room.getMaxAmount())).setScore(8);
        objective.getScore(Bukkit.getOfflinePlayer("  ")).setScore(7);

        for (PlayerData data : room.getWatingUsers()) {
            if(GameScoreBoard.userScoreboards.get(data.getPlayer()) == null || !(GameScoreBoard.userScoreboards.get(data.getPlayer()) instanceof  WaitRoomScoreBoard) ){
                GameScoreBoard.userScoreboards.put(data.getPlayer(), this);
            }
            data.getPlayer().setScoreboard(this.scoreboard);
        }
    }

    @Override
    public void delete() {
        for(PlayerData data : room.getWatingUsers())
            data.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

}
