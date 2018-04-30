package com.gamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by user on 2017-11-29.
 */
public class GuiManager {

    private Inventory inventory;

    public GuiManager(int size, String name){
        this.inventory = Bukkit.createInventory(null, size ,name);
    }

    public void setLoopItem(int startSlot, ItemStack itemStack){
        //startSlot 부터 itemStack 으로 꽉 채움
        if(startSlot > this.inventory.getSize()){
            return;
        }

        for(int i = startSlot; i < this.inventory.getSize(); i++){
            this.inventory.setItem(i, itemStack);
        }
    }

    public void setLoopTargetItem(int startSlot, int finSlot, ItemStack itemStack){
        if(finSlot > this.inventory.getSize()){
            return;
        }

        for(int i = startSlot; i < finSlot; i++){
            this.inventory.setItem(i, itemStack);
        }
    }

    public GuiManager setItem(int slot, ItemStack item){
        this.inventory.setItem(slot, item);
        return this;
    }

    public Inventory getInventory(){
        return this.inventory;
    }

}
