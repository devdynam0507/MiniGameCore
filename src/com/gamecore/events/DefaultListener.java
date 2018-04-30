package com.gamecore.events;

import com.effects.EffectsMenu;
import com.gamecore.MiniGame;
import com.gamecore.abstraction.GameRoom;
import com.gamecore.abstraction.GameScoreBoard;
import com.gamecore.abstraction.SoloGame;
import com.gamecore.data.PlayerData;
import com.gamecore.menu.GameMenu;
import com.gamecore.menu.SoloGameMenu;
import com.gamecore.modules.GameModule;
import com.gamecore.modules.GameModuleManager;
import com.gamecore.modules.PluginModule;
import com.gamecore.modules.PluginModuleManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import xql.uninhabited.Island;
import xql.uninhabited.gui.IslandMenuGUI;

import java.util.Collection;

/**
 * Created by user on 2017-12-22.
 */
public class DefaultListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer() == null || e.getPlayer().getName().equals("fakeman"))
        {
            return;
        }
        PlayerData data = PlayerData.get(e.getPlayer().getName());
        if(MiniGame.instance.spawn != null){
            e.getPlayer().teleport(MiniGame.instance.spawn);
            e.getPlayer().getLocation().setPitch(MiniGame.instance.spawn.getPitch());
            e.getPlayer().getLocation().setYaw(MiniGame.instance.spawn.getYaw());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        PlayerData.get(e.getPlayer().getName()).quit();
        if(GameScoreBoard.userScoreboards.get(e.getPlayer()) != null){
            GameScoreBoard.userScoreboards.get(e.getPlayer()).delete();
            GameScoreBoard.userScoreboards.remove(e.getPlayer());
        }
        Collection<PotionEffect> effects = e.getPlayer().getActivePotionEffects();
        for(PotionEffect effect : effects){
            e.getPlayer().removePotionEffect(effect.getType());
        }
    }

    @EventHandler
    public void menuClick(PlayerInteractEvent e){
        if(e.getPlayer().getItemInHand() != null && e.getPlayer().getWorld().equals(MiniGame.instance.spawn.getWorld())){
            if(!e.getPlayer().isOp() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getItemInHand().getType() != Material.AIR && e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null){
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 2f ,1f);
                if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals("게임")){
                    GameMenu.menu().open(e.getPlayer());
                    e.setCancelled(true);
                }else if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals("무인도")){
                    PluginModule module = PluginModuleManager.module().getModule("D-Uninhabited");
                    if(PluginModuleManager.module().isRegistered(module)) {
                        if (Island.hasIsland(e.getPlayer())) {
                            e.getPlayer().openInventory(new IslandMenuGUI().makeItemStack().makeTitle("§a무인도 메뉴").size(9).build());
                        } else {
                            Island.m.createIsland(e.getPlayer(), true);
                        }
                        e.setCancelled(true);
                    }else{
                        e.getPlayer().sendMessage("§c현재 무인도 플러그인이 활성화 되지 않았습니다. 관리자에게 문의하세요");
                        e.setCancelled(true);
                    }
                }else if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals("솔로게임")){
                    SoloGameMenu.menu().open(e.getPlayer());
                    e.setCancelled(true);
                }else if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals("치장")){
                    PluginModule module = PluginModuleManager.module().getModule("MiniGameEffects");
                    if(PluginModuleManager.module().isRegistered(module)){
                        EffectsMenu.menu().open(e.getPlayer());
                        e.setCancelled(true);
                    }else{
                        e.getPlayer().sendMessage("§c현재 치장 플러그인이 활성화 되지 않았습니다. 관리자에게 문의하세요");
                        e.setCancelled(true);
                    }
                }else if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals("내 정보")){
                    PlayerData.get(e.getPlayer().getName()).openInfomation();
                    e.setCancelled(true);
                }
            }
        }
        if(e.getPlayer().getItemInHand() != null && e.getPlayer().getWorld().equals(MiniGame.instance.waitRoomSpawn.getWorld())){
            if(!e.getPlayer().isOp() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getItemInHand().getType() != Material.AIR && e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null){
                if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals("나가기")){
                    PlayerData.get(e.getPlayer().getName()).quit();
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void changeBar1(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void menuClosed(InventoryCloseEvent e){
        if(e.getInventory().getTitle().equals("게임목록")){
            GameMenu.menu().close((Player) e.getPlayer());
        }else if(e.getInventory().getTitle().equals("솔로게임")){
            SoloGameMenu.menu().close((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent e){
         if(e.getRightClicked() instanceof Player){
            if(MiniGame.instance.spawn != null && e.getPlayer().getLocation().getWorld().equals(MiniGame.instance.spawn.getWorld()) && ChatColor.stripColor(((Player) e.getRightClicked()).getName()).contains("Games")){
                e.setCancelled(true);
                GameMenu.menu().open(e.getPlayer());
            }else if(MiniGame.instance.spawn != null && e.getPlayer().getLocation().getWorld().equals(MiniGame.instance.spawn.getWorld()) && ChatColor.stripColor(((Player) e.getRightClicked()).getName()).contains("Solo")){
                e.setCancelled(true);
                SoloGameMenu.menu().open(e.getPlayer());
            }else if(MiniGame.instance.spawn != null && e.getPlayer().getLocation().getWorld().equals(MiniGame.instance.spawn.getWorld()) && ChatColor.stripColor(((Player) e.getRightClicked()).getName()).contains("무인도")){
                e.setCancelled(true);
                PluginModule module = PluginModuleManager.module().getModule("D-Uninhabited");
                if(PluginModuleManager.module().isRegistered(module)) {
                    if (Island.hasIsland(e.getPlayer())) {
                        e.getPlayer().openInventory(new IslandMenuGUI().makeItemStack().makeTitle("§a무인도 메뉴").size(9).build());
                    } else {
                        Island.m.createIsland(e.getPlayer(), true);
                    }
                    e.setCancelled(true);
                }else{
                    e.getPlayer().sendMessage("§c현재 무인도 플러그인이 활성화 되지 않았습니다. 관리자에게 문의하세요");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void noDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player && (((Player) e.getEntity()).getWorld().equals(MiniGame.instance.spawn.getWorld()) || ((Player) e.getEntity()).getWorld().equals(MiniGame.instance.waitRoomSpawn.getWorld()))){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void menuClick_inv(InventoryClickEvent e){
        if(e.getInventory().getTitle().equals("게임목록")){
            e.setCancelled(true);
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getRawSlot() > 54)
                return;

            if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
                PlayerData.get(e.getWhoClicked().getName()).joinGameRoom(GameRoom.getRoom(e.getCurrentItem().getItemMeta().getDisplayName()));
            }
        }else if(e.getInventory().getTitle().equals("솔로게임")){
            e.setCancelled(true);
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getRawSlot() > 54)
                return;

            if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
                for(GameModule module : GameModuleManager.module().getModules()){
                    if(GameModuleManager.module().isRegistered(module) && module instanceof SoloGame){
                        if(((SoloGame) module).getCustomName().equals(e.getCurrentItem().getItemMeta().getDisplayName())){
                            ((SoloGame) module).open((Player) e.getWhoClicked());
                        }
                    }
                }
            }
        }
    }

}
