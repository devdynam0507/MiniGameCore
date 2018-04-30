package com.gamecore.defaultfunctions;

import com.gamecore.abstraction.GameScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

/**
 * Created by user on 2017-12-27.
 */
public class CountDownScoreBoard extends GameScoreBoard {

    private int c;

    public CountDownScoreBoard(Player player) {
        super(player);
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective(player.getName(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§c§lReady!");
    }

    @Override
    public void floatBoard() {
        this.delete();
        super.objective = super.scoreboard.registerNewObjective(this.player.getName(), "dummy");
        super.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        super.objective.setDisplayName("§c§lReady!");

        objective.getScore(Bukkit.getOfflinePlayer("  ")).setScore(3);
        objective.getScore(Bukkit.getOfflinePlayer("§e게임시작 §f" + c + " §e초전")).setScore(2);
        objective.getScore(Bukkit.getOfflinePlayer(" ")).setScore(1);

        player.setScoreboard(this.scoreboard);
    }

    @Override
    public void delete() {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public void set(int c){
        this.c = c;
    }
}
