package com.gamecore.commands;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.ItemManager;
import com.gamecore.data.PlayerData;
import com.gamecore.modules.GameModule;
import com.gamecore.modules.GameModuleManager;
import com.gamecore.modules.PluginModule;
import com.gamecore.modules.PluginModuleManager;
import com.mitem.itemtypes.ItemObjectType;
import com.mitem.itemtypes.ItemType;
import com.mitem.wrappers.ItemTags;
import com.mitem.wrappers.ItemWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by user on 2017-12-21.
 */
public class CoreCommands implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equals("미니게임") && sender.isOp()){
            if(args.length == 0){
                sender.sendMessage("/미니게임 unregister [name]");
                sender.sendMessage("/미니게임 aur - 전체 게임을 unregister 합니다");
                sender.sendMessage("==-- 미니게임 register은 해당 미니게임 플러그인 커맨드를 참조하세요 --==");
            }else if(args.length == 2){
                if(args[0].equals("unregister") && args[1] != null){
                    GameModule module = GameModuleManager.module().getModule(args[1]);
                    if(module != null){
                        GameModuleManager.module().unregister(module);
                        sender.sendMessage("§a해당 미니게임을 unregister 시켰습니다");
                    }else{
                        sender.sendMessage("§c해당 미니게임은 없는 게임입니다.");
                    }
                }
            }else if(args.length == 1){
                if(args[0].equals("aur")){
                    GameModuleManager.module().unregisterAll();
                    sender.sendMessage("§a전체 미니게임을 unregister 시켰습니다");
                }
            }
        }else if(label.equals("플러그인") && sender.isOp()){
            if(args.length == 0){
                sender.sendMessage("/플러그인 register [name]");
                sender.sendMessage("/플러그인 unregister [name]");
                sender.sendMessage("/플러그인 aur - 전체 플러그인을 unregister 합니다");
            }else if(args.length == 2){
                if(args[0].equals("unregister") && args[1] != null){
                    PluginModule module = PluginModuleManager.module().getModule(args[1]);
                    if(module != null){
                        PluginModuleManager.module().unregister(module);
                        sender.sendMessage("§a해당 플러그인을 unregister 시켰습니다");
                    }else{
                        sender.sendMessage("§c해당 플러그인은 없는 플러그인입니다.");
                    }
                }else if(args[0].equals("register") && args[1] != null){
                    PluginModule module = PluginModuleManager.module().getModule(args[1]);
                    if(module == null){
                        Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
                        plugin.getPluginLoader().enablePlugin(plugin);
                        sender.sendMessage("§a해당 플러그인을 register 시켰습니다");
                    }else{
                        sender.sendMessage("§c해당 플러그인은 없는 플러그인입니다.");
                    }
                }
            }else if(args.length == 1){
                if(args[0].equals("aur")){
                    PluginModuleManager.module().unregisterAll();
                    sender.sendMessage("§a전체 플러그인을 unregister 시켰습니다");
                }
            }
        }

        if(args.length == 0 && sender.isOp()) {
            if (label.equals("스폰설정")) {
                MiniGame.instance.setSpawn(((Player) sender).getLocation());
                ((Player)sender).getLocation().getWorld().setSpawnLocation(((Player) sender).getLocation().getBlockX(), ((Player) sender).getLocation().getBlockY(), ((Player) sender).getLocation().getBlockZ());
                sender.sendMessage("스폰이 설정되었습니다.");
            }

            if(label.equals("대기실설정") &&  sender.isOp()){
                MiniGame.instance.setWaitSpawn(((Player) sender).getLocation());
                ((Player)sender).getLocation().getWorld().setSpawnLocation(((Player) sender).getLocation().getBlockX(), ((Player) sender).getLocation().getBlockY(), ((Player) sender).getLocation().getBlockZ());
                sender.sendMessage("대기실이 설정되었습니다.");
            }

        }
        if(args.length == 0){
            if(label.equals("서버")){
                sender.sendMessage(" ");
                sender.sendMessage("§d디스코드( 문의 )§f: 개발1#2712");
                sender.sendMessage("§a카페주소§f: http://cafe.naver.com/minecraftminigames");
                sender.sendMessage(" ");
            }else if(label.equals("스폰")){
                MiniGame.instance.teleport((Player) sender);
            }else if(label.equals("test")){
                ItemWrapper item = ItemManager.getItem(ItemType.PARTICLE, "heart", (Player) sender);
                PlayerData.get(sender.getName()).addItem(ItemType.PARTICLE, item);
            }else if(label.equals("test2")){
                ItemTags tag = new ItemTags().setDate("2018-1-28").setItemCustomName("§dHeart").setItemName("heart")
                        .setItemType(ItemType.PARTICLE).setObjectType(ItemObjectType.TermItem).setPlayerName(sender
                                .getName()).build();
                try
                {
                    PlayerData.get(sender.getName()).remove(tag);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

}
