package com.gamecore.abstraction;

import com.gamecore.MiniGame;
import com.gamecore.data.DataManager;
import com.gamecore.data.PlayerData;

import com.gamecore.defaultfunctions.WaitRoomScheduler;
import com.gamecore.defaultfunctions.WaitRoomScoreBoard;
import com.gamecore.modules.GameModule;
import com.gamecore.modules.GameModuleManager;
import org.bukkit.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 게임 대기실 클래스 */
public class GameRoom {

    private static List<GameRoom> gameRooms = new ArrayList<>(); /** 전체 대기실 */

    protected int maxAmount; /** 최대 인원 */
    protected List<PlayerData> watingUsers = new ArrayList<>(); /** 대기중인 유저들 */
    protected GameModule game; /** 해당 게임 대기실의 게임 모듈 */
    protected DataManager manager; /** 게임 대기실 Config */
    protected Material material; /** GUI에 나타낼 Material */

    protected String roomName; /** 대기실 이름 */

    public GameRoom(String name, int maxAmount, GameModule module, Material material){
        this.roomName = name;
        this.maxAmount = maxAmount;
        this.game = module;
        this.material = material;
    }

    public Game getGame(){
        return (Game) this.game;
    }

    /** 대기중인 플레이어의 인원 반환 */
    public int getWatingUsersAmount(){
        return watingUsers.size();
    }

    /** 최대 플레이어 반환 */
    public int getMaxAmount(){
        return this.maxAmount;
    }

    /** GUI에 나타낼 아이콘 반환 */
    public Material getMenuType(){
        return this.material;
    }

    /** 대기실 입장 메소드 */
    public void join(PlayerData data){
        if(!watingUsers.contains(data) && GameModuleManager.module().isRegistered(this.game)){
            watingUsers.add(data);
            data.getPlayer().teleport(MiniGame.instance.waitRoomSpawn);
            data.getPlayer().getLocation().setPitch(MiniGame.instance.waitRoomSpawn.getPitch());
            data.getPlayer().getLocation().setYaw(MiniGame.instance.waitRoomSpawn.getYaw());
            GameRoomManager.getManager().join(data.getPlayer(), this);
            if(watingUsers.size() == this.maxAmount/2){
                WaitRoomScheduler scheduler = new WaitRoomScheduler();
                scheduler.setGameRoom(this);
                scheduler.start();
            }
        }
    }

    /** 대기실 퇴장 메소드 */
    public void quit(PlayerData data){
        if(watingUsers.contains(data)){
            watingUsers.remove(data);
            GameRoomManager.getManager().quit(data.getPlayer());
            MiniGame.instance.teleport(data.getPlayer());
        }
    }

    /** 대기실 생성 메소드 */
    public void create(){
        if(getRoom(this.roomName) == null){
            File dir = new File(MiniGame.dir, "gameroom");
            this.manager = new DataManager(dir, this.roomName + ".yml");
            this.manager.set("name", this.roomName);
            this.manager.set("maxAmount", this.maxAmount);
            this.manager.set("game", this.game.getName());
            this.manager.set("menutype", this.material.toString());
            try {
                this.manager.save();
            }catch (Exception e){
                e.printStackTrace();
            }
            gameRooms.add(this);
            new WaitRoomScoreBoard(this);
        }
    }


    public void setDataManager(DataManager manager){
        this.manager = manager;
    }

    public String getName(){
        return this.roomName;
    }

    public List<PlayerData> getWatingUsers(){
        return this.watingUsers;
    }

    /** 게임 대기실 게임 모듈 등록 */
    public void setModule(GameModule module){
        this.game = module;
    }

    /** 대기실을 검색하여 반환합니다.
     * @param name
     * @return GameRoom */
    public static GameRoom getRoom(String name){
        for(GameRoom room : gameRooms){
            if(GameModuleManager.module().isRegistered(room.game) && (room.getName().equals(name) || room.getGame().getCustomName().equals(name)))
                return room;
        }

        return null;
    }

    /** 전체 대기실을 반환합니다. */
    @Deprecated
    public static List<GameRoom> getGameRooms(){
        List<GameRoom> filtered = new ArrayList<>();
        for(GameRoom room : gameRooms){
            if(GameModuleManager.module().isRegistered(room.getGame().getModule())){
                filtered.add(room);
            }
        }
        return filtered;
    }

}
