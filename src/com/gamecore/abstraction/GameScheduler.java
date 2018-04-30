package com.gamecore.abstraction;

import org.bukkit.Bukkit;

public abstract class GameScheduler {

    protected int id; /** Scheduler ID */

    public abstract void start(); /** 게임 스케줄러 시작 */

    /** 게임 스케줄러 캔슬 */
    public void stop(){
        Bukkit.getScheduler().cancelTask(id);
    }

}
