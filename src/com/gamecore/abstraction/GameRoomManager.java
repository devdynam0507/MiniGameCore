package com.gamecore.abstraction;

import com.gamecore.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017-12-21.
 */
public class GameRoomManager extends GameScheduler {

    private static GameRoomManager instance;

    private HashMap<Player, GameRoom> inRooms = new HashMap<>();

    private GameRoomManager(){}

    /** 대기실 매니저 싱글톤객체 반환
     * @return GameRoomManager */
    public static GameRoomManager getManager(){
        if(instance == null) instance = new GameRoomManager();
        return instance;
    }

    /** 게임 대기실 입장 */
    public void join(Player player, GameRoom room){
        this.inRooms.put(player, room);
        for(Map.Entry<Player, GameRoom> entry : inRooms.entrySet()){
            if(!entry.getValue().equals(room)){
                entry.getKey().hidePlayer(player);
            }
        }
    }

    /** 게임 대기실 퇴장 */
    public void quit(Player player){
        if(inRooms.containsKey(player)){
            GameRoom room = inRooms.get(player);
            inRooms.remove(player);
            for(Map.Entry<Player, GameRoom> entry : inRooms.entrySet()){
                if(!room.equals(entry.getValue())){
                    entry.getKey().showPlayer(player);
                }
            }
        }
    }

    /** 플레이어가 맵 밖으로 벗어나지 않도록 플레이어 위치를 5tick 마다 체크해주는 Task */
    @Override
    public void start() {
        super.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiniGame.instance, new Runnable() {
            @Override
            public void run() {
                for(Map.Entry<Player, GameRoom> entry : inRooms.entrySet()){
                    if(entry.getKey().getLocation().getWorld().equals(MiniGame.instance.waitRoomSpawn.getWorld()) && (entry.getKey().getLocation().getY() == 0 || entry.getKey().getLocation().getY() < 0))
                    {
                        entry.getKey().teleport(MiniGame.instance.waitRoomSpawn);
                        entry.getKey().getLocation().setPitch(MiniGame.instance.waitRoomSpawn.getPitch());
                        entry.getKey().getLocation().setYaw(MiniGame.instance.waitRoomSpawn.getYaw());
                    }
                }
            }
        }, 0L , 5L);
    }
}
