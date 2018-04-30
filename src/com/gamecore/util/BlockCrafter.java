package com.gamecore.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Created by user on 2017-05-26.
 */
public class BlockCrafter {

    private Block block;

    private String stringBlock;


    public BlockCrafter(Block block){
        this.block = block;
    }

    public BlockCrafter(String block){
        this.stringBlock = block;
    }

    public String toString(){
        String result = block.getTypeId() + "," + block.getData() + "," + new LocationCrafter(block.getLocation()).toString();
        return result;
    }

    public void setBlock(){
        String[] values = stringBlock.split(",");
        String loc = new StringBuilder().append(values[2]).append(",").append(values[3]).append(",").append(values[4]).append(",").append(values[5]).toString();
        Location location = new LocationCrafter(loc).toLocation();
        location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()).setTypeId(Integer.parseInt(values[0]));
        location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()).setData(Byte.parseByte(values[1]));
    }

}
