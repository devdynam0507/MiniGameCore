package com.gamecore.menu;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.Game;
import com.gamecore.abstraction.GameRoom;
import com.gamecore.util.GuiManager;
import com.gamecore.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by user on 2017-12-25.
 */
public class GameMenu extends Menu{

    private static GameMenu instance;

    public static GameMenu menu(){
        if(instance == null) instance = new GameMenu();
        return instance;
    }

    private GameMenu(){
    }

    public void open(Player player){
        this.openingInventory.put(player, new GuiManager(54, "게임목록"));
        player.openInventory(this.openingInventory.get(player).getInventory());
    }

    public void close(Player player){
        if(openingInventory.containsKey(player))
            openingInventory.remove(player);
    }

    /** 게임 목록창을 열었을때 실시간 데이터 동기화를 위한 Task */
    @Override
    public void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiniGame.instance, new Runnable() {
            @Override
            public void run() {
                GuiManager manager = new GuiManager(54, "게임목록");
                List<GameRoom> rooms = GameRoom.getGameRooms();
                if(rooms.size() > 0) {
                    Iterator<Player> iter = openingInventory.keySet().iterator();
                    while(iter.hasNext()){
                        Player player = iter.next();
                        for(int i = 0; i <  rooms.size(); i++){
                            GameRoom room = rooms.get(i);
                            ItemBuilder builder = new ItemBuilder(room.getMenuType());
                            Game game = room.getGame();
                            int maxAmount = room.getMaxAmount();
                            int inUsers = room.getWatingUsersAmount();
                            List<String> gameLore = game.getGameLore();
                            builder.setAmounts(1).setDisplayName(game.getCustomName()).addLore(" ").addLore("§7인원§f: " + inUsers + "/" + maxAmount).addLore("§a상태§f: " + game.getRunningType().toString()).addLore(" ").build();
                            for (String lore : gameLore) {
                                builder.addLore(lore);
                            }
                            openingInventory.get(player).setItem(i, builder.build());
                        }
                    }
                }
            }
        }, 0L, 10L);
    }



    public void stop(){
        for(Player player : this.openingInventory.keySet()){
            player.closeInventory();
        }

        openingInventory.clear();
        openingInventory = null;

        super.refreshed = false;

        super.stop();
    }
}
