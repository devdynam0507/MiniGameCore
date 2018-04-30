package com.gamecore.menu;

import com.gamecore.abstraction.GameScheduler;
import com.gamecore.util.GuiManager;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by user on 2017-12-31.
 */
public abstract class Menu extends GameScheduler{

    protected boolean refreshed;
    protected HashMap<Player, GuiManager> openingInventory = new HashMap<>();

    public abstract void open(Player player);     /** 게임 목록창을 오픈합니다. */
    public abstract void close(Player player);    /** 게임 목록창을 닫을때 데이터를 수거합니다. */

}
