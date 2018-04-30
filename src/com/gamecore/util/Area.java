package com.gamecore.util;

import com.gamecore.data.DataManager;
import com.gamecore.events.AreaListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017-05-26.
 */
public class Area {

    private static Area instance;

    private Area(){}

    public static Area getInstance() {
        if(instance == null) instance = new Area();
        return instance;
    }

    /** 구역을 생성합니다. 생성후 데이터를 저장합니다. */
    public void createArea(DataManager manager){
        Location loc1 = AreaListener.LOCATION_LIST.get(AreaListener.CLICK_TYPE.RIGHT_CLICK);
        Location loc2 = AreaListener.LOCATION_LIST.get(AreaListener.CLICK_TYPE.LEFT_CLICK);
        Location[] loopLocation = this.getLoopLocation(loc1, loc2);
        //0 == big 1 == small
        int averageRadius = Math.abs(((loc1.getBlockX()-loc2.getBlockX())+(loc1.getBlockZ()-loc2.getBlockZ()))/2);
        Location center = new Location(loc1.getWorld(), (loc1.getBlockX()+loc2.getBlockX())/2, loopLocation[1].getBlockY() , (loc1.getBlockZ()+loc2.getBlockZ())/2);
        manager.set("big", new LocationCrafter(loopLocation[0]).toDeepString());
        manager.set("small", new LocationCrafter(loopLocation[1]).toDeepString());
        manager.set("radius", averageRadius);
        manager.set("center", new LocationCrafter(center).toString());

        List<String> list = new ArrayList<>();
        for(Map.Entry<Location, Block> entry : this.getListLocation(loopLocation[1], loopLocation[0]).entrySet()){
            list.add(new BlockCrafter(entry.getValue()).toString());
        }
        manager.set("list", list);
        try{
            manager.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<Block> getListBlock(List<Location> locations){
        List<Block> lists = new ArrayList<>();
        for(Location location : locations){
            Block block = location.getBlock();
            lists.add(block);
        }
        return lists;
    }

    public HashMap<Location, Block> getListLocation(Location small, Location big){
        int smallX = small.getBlockX(), smallY = small.getBlockY(), smallZ = small.getBlockZ();
        int bigX = big.getBlockX(), bigY = big.getBlockY(), bigZ = big.getBlockZ();
        System.out.println(small);
        System.out.println(big);
        HashMap<Location, Block> blockList = new HashMap<>();
        for(int x = smallX; x < bigX+1; x++){
            for(int y = smallY; y < bigY; y++){
                for(int z = smallZ; z < bigZ+1; z++){
                    Location location =new Location(big.getWorld(), x,y,z);
                    blockList.put(location, location.getBlock());
                }
            }
        }
        return blockList;
    }

    public Location[] getLoopLocation(Location location1, Location location2){
        World world = location1.getWorld();
        int eX[] = this.equals(location1.getBlockX(), location2.getBlockX());
        int eY[] = this.equals(location1.getBlockY(), location2.getBlockY());
        int eZ[] = this.equals(location1.getBlockZ(), location2.getBlockZ());
        System.out.println(eX[0] +" , " + eX[1]);
        System.out.println(eY[0] +" , " + eY[1]);
        System.out.println(eZ[0] +" , " + eZ[1]);
        return new Location[] {new Location(world,eX[0], eY[0], eZ[0]), new Location(world, eX[1], eY[1], eZ[1])}; // 0 big 1 small
    }

    private int[] equals(int num, int num2){
        if(num > num2)
            return new int[]{ num, num2};
        else if(num < num2)
            return new int[]{ num2, num};
        return new int[] {num, num2};
    }


}
