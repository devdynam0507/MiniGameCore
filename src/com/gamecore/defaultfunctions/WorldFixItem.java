package com.gamecore.defaultfunctions;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.GameScheduler;
import com.gamecore.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/** 각 월드마다 고정할 아이템 클래스
 * 상속관계가 잘못됬음 기능적 상속만 받음 */
public class WorldFixItem extends GameScheduler {

    private static ItemStack[] spawnWorld = new ItemStack[]{new ItemBuilder(Material.BLAZE_ROD).setAmounts(1).setDisplayName("§a게임").build(), new ItemBuilder(Material.STICK).setAmounts(1).setDisplayName("§a솔로게임").build(), null, new ItemBuilder(Material.GRASS).setAmounts(1).setDisplayName("§a무인도").build(), null, null, null, new ItemBuilder(Material.ENDER_CHEST).setAmounts(1).setDisplayName("§a내 정보").build(), new ItemBuilder(Material.CHEST).setAmounts(1).setDisplayName("§e치장").build()};
    private static ItemStack[] waitRoomWorld = new ItemStack[]{new ItemBuilder(Material.BED).setAmounts(1).setDisplayName("§c나가기").build(), null, null, null, null, null, null, null, null};


    public static void setSpawnWorldItems(Player player){
        for(int i = 0; i < 9; i++){
            player.getInventory().setItem(i, spawnWorld[i]);
        }
    }

    public static void setWaitRooWorldItems(Player player){
        for(int i = 0; i < 9; i++){
            player.getInventory().setItem(i, waitRoomWorld[i]);
        }
    }

    @Override
    public void start() {
        super.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiniGame.instance, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(player.isOp())
                        continue;
                    if(MiniGame.instance.spawn != null && player.getLocation().getWorld().equals(MiniGame.instance.spawn.getWorld())){
                        setSpawnWorldItems(player);
                        continue;
                    }else if(MiniGame.instance.waitRoomSpawn != null && player.getLocation().getWorld().equals(MiniGame.instance.waitRoomSpawn.getWorld())){
                        setWaitRooWorldItems(player);
                    }
                }
            }

        },0L, 10L);
    }
}
