package com.gamecore.defaultfunctions;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.GameRoom;
import com.gamecore.abstraction.GameScheduler;
import com.gamecore.abstraction.GameScoreBoard;
import com.gamecore.abstraction.RunningType;
import com.gamecore.data.PlayerData;
import org.bukkit.Bukkit;

/**
 * Created by user on 2017-12-27.
 */
public class WaitRoomScheduler extends GameScheduler {

    private int count = 15;
    private GameRoom room;

    @Override
    public void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiniGame.instance, new Runnable() {
            @Override
            public void run() {
                if(count == 0){
                    room.getGame().start(room.getWatingUsers());
                    stop();
                }else{
                    if(room.getWatingUsersAmount() < room.getMaxAmount()/2 || room.getWatingUsersAmount() == 0){
                        room.getGame().run = RunningType.WAITING;
                        for(PlayerData data : room.getWatingUsers()){
                            if(GameScoreBoard.userScoreboards.get(data.getPlayer()) != null){
                                GameScoreBoard.userScoreboards.get(data.getPlayer()).delete();
                                GameScoreBoard.userScoreboards.remove(data.getPlayer());
                                continue;
                            }
                        }
                        new WaitRoomScoreBoard(room);
                        stop();
                        return;
                    }
                    for(PlayerData data : room.getWatingUsers()){
                        if(GameScoreBoard.userScoreboards.get(data.getPlayer()) == null){
                            new CountDownScoreBoard(data.getPlayer()).set(count);
                        }else{
                            ((CountDownScoreBoard) GameScoreBoard.userScoreboards.get(data.getPlayer())).set(count);
                        }
                    }
                    count--;
                }
            }
        }, 0L, 20L);
    }

    public void setGameRoom(GameRoom room){
        this.room = room;
        room.getGame().run = RunningType.READY;
    }
}
