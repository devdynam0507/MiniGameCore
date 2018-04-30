package com.gamecore.defaultfunctions;

import com.gamecore.abstraction.GameScoreBoard;
import com.google.common.base.Splitter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import java.util.*;

/**
 * Created by user on 2017-12-30.
 */
public class TestScoreBoard extends GameScoreBoard {

    private List<Team> teams = new ArrayList<>();
    private int a = 100000;

    public TestScoreBoard(Player player) {
        super(player);
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective(player.getName(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§e§lTest!!!");
    }

    @Override
    public void floatBoard() {
        this.delete();
        List<String> list = Arrays.asList(new String[]{"§a아아아아아아아아아아마암아마아아아앙아ㅏ아아아아아아아 " + a});
        for(int i = 0; i < list.size(); i++){
            Map.Entry<Team, String> map = this.updateTeam(list.get(i), i , this.objective.getDisplayName());
            System.out.println(map);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(map.getValue());
            if(map.getKey() != null){
                map.getKey().addPlayer(offlinePlayer);
            }
            objective.getScore(offlinePlayer).setScore(i);
        }

        a--;
        player.setScoreboard(this.scoreboard);
    }

    @Override
    public void delete() {
//        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
//        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    private Map.Entry<Team, String> updateTeam(String v, int i, String t){
        if(v.length() <= 16){
            return new AbstractMap.SimpleEntry<Team, String>(null, v);
        }
        Iterator<String> iter = Splitter.fixedLength(16).split(v).iterator();
        String s = iter.next();
        String r = iter.next();
        for(Team team : this.teams){
            System.out.println(team.getPrefix());
            System.out.println(v.length());
            if(team.getPrefix().equals(s) || team.getPrefix().contains(s)){
                team.setPrefix(s);
                if(v.length() > 32){
                    team.setSuffix(iter.next());
                }
                return new AbstractMap.SimpleEntry<Team, String>(team, r);
            }
        }
        Team nt = this.scoreboard.registerNewTeam(t + i);
        nt.setPrefix(s);
        if(v.length() > 32){
            nt.setSuffix(iter.next());
        }

        this.teams.add(nt);
        return new AbstractMap.SimpleEntry<Team, String>(nt, r);
    }

    private Map.Entry<Team, String> createTeam(String t, int i, String title){
        String r = "";
        if(t.length() <= 16){
            return new AbstractMap.SimpleEntry<Team, String>(null, t);
        }
        Iterator<String> iter = Splitter.fixedLength(16).split(t).iterator();

        Team team = this.scoreboard.registerNewTeam(title + i);
        team.setPrefix(iter.next());

        r = iter.next();
        if(t.length() > 32){
            team.setSuffix(iter.next());
        }

        this.teams.add(team);
        return new AbstractMap.SimpleEntry<Team, String>(team, r);
    }
}
