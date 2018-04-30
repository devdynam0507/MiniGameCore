package com.gamecore;

import com.gamecore.abstraction.Game;
import com.gamecore.abstraction.GameRoom;
import com.gamecore.abstraction.GameRoomManager;
import com.gamecore.commands.CoreCommands;
import com.gamecore.data.DataManager;
import com.gamecore.defaultfunctions.ScoreBoardScheduler;
import com.gamecore.defaultfunctions.WorldFixItem;
import com.gamecore.events.AreaListener;
import com.gamecore.events.DefaultListener;
import com.gamecore.events.WorldFixItemEvent;
import com.gamecore.menu.GameMenu;
import com.gamecore.menu.SoloGameMenu;
import com.gamecore.util.DefaultFontInfo;
import com.gamecore.util.LocationCrafter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MiniGame extends JavaPlugin {

    public static MiniGame instance;
    public static File dir;

    private DataManager manager;

    public Location spawn;
    public Location waitRoomSpawn;


    private final static int CENTER_PX = 120;

    @Override
    public void onEnable() {
        instance = this;
        dir = this.getDataFolder();
        if(!dir.exists())
            dir.mkdir();

        this.loads();
        this.commands();
        this.loadSpawn();
        Bukkit.getConsoleSender().sendMessage("§a미니게임 코어 플러그인 로드");
    }

    public void events(){
        Bukkit.getPluginManager().registerEvents(new WorldFixItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DefaultListener(), this);
        Bukkit.getPluginManager().registerEvents(new AreaListener(), this);
    }

    public void loads(){
        manager = new DataManager(dir, "config.yml");
        this.events();
        new WorldFixItem().start();
        GameRoomManager.getManager().start();
        GameMenu.menu().start();
        SoloGameMenu.menu().start();
        new ScoreBoardScheduler().start();
    }

    public void commands(){
        this.getCommand("스폰설정").setExecutor(new CoreCommands());
        this.getCommand("대기실설정").setExecutor(new CoreCommands());
        this.getCommand("미니게임").setExecutor(new CoreCommands());
        this.getCommand("플러그인").setExecutor(new CoreCommands());
        this.getCommand("서버").setExecutor(new CoreCommands());
        this.getCommand("스폰").setExecutor(new CoreCommands());
        this.getCommand("test").setExecutor(new CoreCommands());
        this.getCommand("test2").setExecutor(new CoreCommands());
    }

    public void setSpawn(Location location){
        String data = new LocationCrafter(location).toDeepString();
        this.spawn = location;
        this.manager.set("spawn", data);
        try {
            this.manager.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setWaitSpawn(Location location){
        String data = new LocationCrafter(location).toDeepString();
        this.waitRoomSpawn = location;
        this.manager.set("waitspawn", data);
        try {
            this.manager.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadSpawn(){
        if(this.manager.get("spawn") != null){
            this.spawn = new LocationCrafter(this.manager.getString("spawn")).toDeppLocation();
        }

        if(this.manager.get("waitspawn") != null){
            this.waitRoomSpawn = new LocationCrafter(this.manager.getString("waitspawn")).toDeppLocation();
        }
        System.out.println(this.spawn);
        System.out.println(this.waitRoomSpawn);
    }

    public void teleport(Player player){
        if(spawn == null) return;
        player.teleport(this.spawn);
        player.getLocation().setPitch(this.spawn.getPitch());
        player.getLocation().setYaw(this.spawn.getYaw());
    }

    public static void sendCenteredMessage(Player player, String message){
        if(player == null) return;
        if(message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = false;
                continue;
            }else if(previousCode == true){
                previousCode = true;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }
}
