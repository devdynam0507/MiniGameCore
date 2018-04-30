package com.gamecore.menu;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.SoloGame;
import com.gamecore.modules.GameModule;
import com.gamecore.modules.GameModuleManager;
import com.gamecore.util.GuiManager;
import com.gamecore.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 2017-12-31.
 */
public class SoloGameMenu extends Menu {

    private static SoloGameMenu instance;

    private SoloGameMenu(){
    }

    public static SoloGameMenu menu(){
        if(instance == null){
            instance = new SoloGameMenu();
            return instance;
        }
        return  instance;
    }

    @Override
    public void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiniGame.instance, new Runnable() {
            @Override
            public void run() {
                List<GameModule> modules = GameModuleManager.module().getModules();
                Iterator<Player> iter = SoloGameMenu.super.openingInventory.keySet().iterator();
                while(iter.hasNext()) {
                    int i = 0;
                    for (GameModule module : modules) {
                        if (module instanceof SoloGame) {
                            SoloGame game = (SoloGame) module;
                            Player player = iter.next();
                            ItemBuilder builder = new ItemBuilder(game.type());
                            builder.setDisplayName(game.getCustomName());
                            for (String s : game.getGameLore()) {
                                builder.addLore(s);
                            }

                            openingInventory.get(player).setItem(i, builder.build());
                            i++;
                        }
                    }
                }

            }
        }, 0L, 20L);
    }

    @Override
    public void stop() {
        for(Player player : this.openingInventory.keySet()){
            player.closeInventory();
        }

        openingInventory.clear();
        openingInventory = null;

        super.refreshed = false;
        super.stop();
    }

    @Override
    public void open(Player player) {
        this.openingInventory.put(player, new GuiManager(54, "솔로게임"));
        player.openInventory(this.openingInventory.get(player).getInventory());
    }

    @Override
    public void close(Player player) {
        if(openingInventory.containsKey(player))
            openingInventory.remove(player);
    }
}
