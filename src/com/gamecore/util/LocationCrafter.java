package com.gamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by user on 2017-05-26.
 */
public class LocationCrafter {

    private Location location;
    private String stringLocation;

    public LocationCrafter(Location location){
        this.location = location;
    }

    public LocationCrafter(String location){
        this.stringLocation = location;
    }
    //x y z world
    @Override
    public String toString(){
        return String.valueOf(location.getBlockX()) + "," + String.valueOf(location.getBlockY()) + "," + String.valueOf(location.getBlockZ()) + "," + location.getWorld().getName();
    }

    public String toDeepString(){
        return this.toString() + "," + location.getPitch() + "," + location.getYaw();
    }

    public Location toLocation(){
        String[] values = stringLocation.split(",");
        return new Location(Bukkit.getWorld(values[3]), Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
    }

    public Location toDeppLocation(){
        String[] values = stringLocation.split(",");
        Location location = new Location(Bukkit.getWorld(values[3]), Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
        location.setPitch(Float.parseFloat(values[4]));
        location.setYaw(Float.parseFloat(values[5]));
        return location;
    }

}
