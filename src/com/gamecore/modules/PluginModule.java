package com.gamecore.modules;

import org.bukkit.plugin.Plugin;

/**
 * Created by user on 2017-12-29.
 */
public abstract class PluginModule {

    private Plugin plugin;
    private int status;

    protected PluginModule(Plugin plugin){
        this.plugin = plugin;
    }

    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public abstract boolean load();

    public Plugin getPlugin(){
        return this.plugin;
    }

}
