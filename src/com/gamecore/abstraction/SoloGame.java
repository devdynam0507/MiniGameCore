package com.gamecore.abstraction;

import com.gamecore.data.DataManager;
import com.gamecore.modules.GameModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/** 1인 게임 상위 클래스
 * */
public abstract class SoloGame extends GameModule {

    private String name;
    protected DataManager manager;

    public SoloGame(String name){
        super(name);
        this.name = name;
    }

    public abstract void open(Player player); /** 1인게임 GUI를 Open합니다. */
    public abstract void start(Player player); /** 1인게임을 시작합니다. */
    public abstract void stop(Player player); /** 1인게임을 중단합니다. */

    public abstract DataManager getDatamanager();

    public abstract String getCustomName();
    public abstract List<String> getGameLore();

    public abstract Material type();

    public String getName(){
        return this.name;
    }

    public GameModule getModule(){
        return this;
    }
}
