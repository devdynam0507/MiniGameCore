package com.gamecore.defaultfunctions;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.GameRoom;
import com.gamecore.abstraction.GameScheduler;
import com.gamecore.abstraction.GameScoreBoard;
import com.gamecore.abstraction.RunningType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017-12-25.
 */
public class ScoreBoardScheduler extends GameScheduler {

    @Override
    public void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiniGame.instance, new Runnable() {
            @Override
            public void run() {

                Iterator<GameRoom> iter = GameScoreBoard.roomSocreBoards.keySet().iterator();

                while(iter.hasNext()){
                    GameRoom next = iter.next();
                    GameScoreBoard scoreBoard = GameScoreBoard.roomSocreBoards.get(next);
                    if(next.getGame().getRunningType() == RunningType.RUNNING || next.getGame().getRunningType() == RunningType.READY){
                        iter.remove();
                        scoreBoard.delete();
                    }else{
                        scoreBoard.floatBoard();
                    }
                }


                for(Map.Entry<Player, GameScoreBoard> entry : GameScoreBoard.userScoreboards.entrySet()){
                    entry.getValue().floatBoard();
                }
            }
        }, 0L, 10L);
    }
}
