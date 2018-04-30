package com.gamecore.events;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.GameScoreBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by user on 2017-12-20.
 */
public class WorldFixItemEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!e.getWhoClicked().isOp()) {
            if (e.getWhoClicked().getWorld().equals(MiniGame.instance.spawn.getWorld())) {
                e.setCancelled(true);
                return;
            }
            if(e.getWhoClicked().getLocation().getWorld().equals(MiniGame.instance.waitRoomSpawn.getWorld())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrops(PlayerDropItemEvent e){
        if(!e.getPlayer().isOp()) {
            if (e.getPlayer().getWorld().equals(MiniGame.instance.spawn.getWorld())) {
                e.setCancelled(true);
                return;
            }
            if(e.getPlayer().getLocation().getWorld().equals(MiniGame.instance.waitRoomSpawn.getWorld())){
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(!e.getPlayer().isOp()) {
            if (e.getPlayer().getWorld().equals(MiniGame.instance.spawn.getWorld())) {
                e.setCancelled(true);
                return;
            }
            if(e.getPlayer().getLocation().getWorld().equals(MiniGame.instance.waitRoomSpawn.getWorld())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e){
        e.getPlayer().getInventory().clear();
        if(GameScoreBoard.userScoreboards.get(e.getPlayer()) != null){
            GameScoreBoard.userScoreboards.get(e.getPlayer()).delete();
            GameScoreBoard.userScoreboards.remove(e.getPlayer());
        }
    }

}
