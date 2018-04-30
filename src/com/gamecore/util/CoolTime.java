package com.gamecore.util;

import com.gamecore.MiniGame;
import com.gamecore.abstraction.GameScheduler;
import org.bukkit.Bukkit;

/**
 * Created by user on 2017-12-24.
 */
public class CoolTime extends GameScheduler{

    private int second;
    private boolean use;

    public boolean getState(){
        return this.use;
    }

    @Override
    public void start() {
        super.id = Bukkit.getScheduler().scheduleSyncDelayedTask(MiniGame.instance, new Runnable() {
            @Override
            public void run() {
                use = true;
                stop();
            }
        }, 20*second);
    }

    @Override
    public void stop() {
        super.stop();
    }

    public CoolTime setCoolTime(int second){
        this.second = second;
        this.use = false;
        this.start();
        return this;
    }
}
