package com.gamecore.events;

import com.gamecore.util.LocationCrafter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

/**
 * Created by user on 2017-05-26.
 */
public class AreaListener implements Listener {

    public enum CLICK_TYPE {RIGHT_CLICK, LEFT_CLICK}

    public static HashMap<CLICK_TYPE, Location> LOCATION_LIST = new HashMap<>();

    @EventHandler
    public void clickArea(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(player.isOp() && e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() == Material.STICK) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                String stringLocation = new LocationCrafter(e.getClickedBlock().getLocation()).toString();
                LOCATION_LIST.put(CLICK_TYPE.LEFT_CLICK, e.getClickedBlock().getLocation());
                e.getPlayer().sendMessage("§7Left Click : " + stringLocation);
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                String stringLocation = new LocationCrafter(e.getClickedBlock().getLocation()).toString();
                LOCATION_LIST.put(CLICK_TYPE.RIGHT_CLICK, e.getClickedBlock().getLocation());
                e.getPlayer().sendMessage("§7Right Click : " + stringLocation);
            }
            e.setCancelled(true);
        }
    }

}
