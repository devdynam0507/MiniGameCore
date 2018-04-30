package com.gamecore.util;

import net.minecraft.server.v1_5_R3.Packet63WorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by user on 2017-06-11.
 */
public class Particle {

    public static void send(Player p, Location loc, float offsetX, float offsetY, float offsetZ, int speed, int count, String type) throws Exception{
        Packet63WorldParticles packet = new Packet63WorldParticles();
        setValue(packet, "a", type);
        setValue(packet, "b", (float)loc.getX());
        setValue(packet, "c", (float)loc.getY());
        setValue(packet, "d", (float)loc.getZ());
        setValue(packet, "e", offsetX);
        setValue(packet, "f", offsetY);
        setValue(packet, "g", offsetZ);
        setValue(packet, "h", speed);
        setValue(packet, "i", count);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }


    private static void setValue(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    private static Object getValue(Object instance, String fieldName) throws Exception{
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }
}

/*

    EXPLOSION_NORMAL("EXPLOSION_NORMAL", 0, fieload("EXPLOSION_NORMAL"), "explode", 0, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    EXPLOSION_LARGE("EXPLOSION_LARGE", 1, fieload("EXPLOSION_LARGE"), "largeexplode", 1, -1, new ParticleProperty[0]),
    EXPLOSION_HUGE("EXPLOSION_HUGE", 2, fieload("EXPLOSION_HUGE"), "hugeexplosion", 2, -1, new ParticleProperty[0]),
    FIREWORKS_SPARK("FIREWORKS_SPARK", 3, fieload("FIREWORKS_SPARK"), "fireworksSpark", 3, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    WATER_BUBBLE("WATER_BUBBLE", 4, fieload("WATER_BUBBLE"), "bubble", 4, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER }),
    WATER_SPLASH("WATER_SPLASH", 5, fieload("WATER_SPLASH"), "splash", 5, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    WATER_WAKE("WATER_WAKE", 6, fieload("WATER_WAKE"), "wake", 6, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SUSPENDED("SUSPENDED", 7, fieload("SUSPENDED"), "suspended", 7, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_WATER }),
    SUSPENDED_DEPTH("SUSPENDED_DEPTH", 8, fieload("SUSPENDED_DEPTH"), "depthSuspend", 8, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    CRIT("CRIT", 9, fieload("CRIT"), "crit", 9, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    CRIT_MAGIC("CRIT_MAGIC", 10, fieload("CRIT_MAGIC"), "magicCrit", 10, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SMOKE_NORMAL("SMOKE_NORMAL", 11, fieload("SMOKE_NORMAL"), "smoke", 11, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SMOKE_LARGE("SMOKE_LARGE", 12, fieload("SMOKE_LARGE"), "largesmoke", 12, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SPELL("SPELL", 13, fieload("SPELL"), "spell", 13, -1, new ParticleProperty[0]),
    SPELL_INSTANT("SPELL_INSTANT", 14, fieload("SPELL_INSTANT"), "instantSpell", 14, -1, new ParticleProperty[0]),
    SPELL_MOB("SPELL_MOB", 15, fieload("SPELL_MOB"), "mobSpell", 15, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 16, fieload("SPELL_MOB_AMBIENT"), "mobSpellAmbient", 16, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    SPELL_WITCH("SPELL_WITCH", 17, fieload("SPELL_WITCH"), "witchMagic", 17, -1, new ParticleProperty[0]),
    DRIP_WATER("DRIP_WATER", 18, fieload("DRIP_WATER"), "dripWater", 18, -1, new ParticleProperty[0]),
    DRIP_LAVA("DRIP_LAVA", 19, fieload("DRIP_LAVA"), "dripLava", 19, -1, new ParticleProperty[0]),
    VILLAGER_ANGRY("VILLAGER_ANGRY", 20, fieload("VILLAGER_ANGRY"), "angryVillager", 20, -1, new ParticleProperty[0]),
    VILLAGER_HAPPY("VILLAGER_HAPPY", 21, fieload("VILLAGER_HAPPY"), "happyVillager", 21, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    TOWN_AURA("TOWN_AURA", 22, fieload("TOWN_AURA"), "townaura", 22, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    NOTE("NOTE", 23, fieload("NOTE"), "note", 23, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    PORTAL("PORTAL", 24, fieload("PORTAL"), "portal", 24, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 25, fieload("ENCHANTMENT_TABLE"), "enchantmenttable", 25, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    FLAME("FLAME", 26, fieload("FLAME"), "flame", 26, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    LAVA("LAVA", 27, fieload("LAVA"), "lava", 27, -1, new ParticleProperty[0]),
    FOOTSTEP("FOOTSTEP", 28, fieload("FOOTSTEP"), "footstep", 28, -1, new ParticleProperty[0]),
    CLOUD("CLOUD", 29, fieload("CLOUD"), "cloud", 29, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    REDSTONE("REDSTONE", 30, fieload("REDSTONE"), "reddust", 30, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    SNOWBALL("SNOWBALL", 31, fieload("SNOWBALL"), "snowballpoof", 31, -1, new ParticleProperty[0]),
    SNOW_SHOVEL("SNOW_SHOVEL", 32, fieload("SNOW_SHOVEL"), "snowshovel", 32, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SLIME("SLIME", 33, fieload("SLIME"), "slime", 33, -1, new ParticleProperty[0]),
    HEART("HEART", 34, fieload("HEART"), "heart", 34, -1, new ParticleProperty[0]),
    BARRIER("BARRIER", 35, fieload("BARRIER"), "barrier", 35, 8, new ParticleProperty[0]),
    ITEM_CRACK("ITEM_CRACK", 36, fieload("ITEM_CRACK"), "iconcrack", 36, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA }),
    BLOCK_CRACK("BLOCK_CRACK", 37, fieload("BLOCK_CRACK"), "blockcrack", 37, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_DATA }),
    BLOCK_DUST("BLOCK_DUST", 38, fieload("BLOCK_DUST"), "blockdust", 38, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA }),
    WATER_DROP("WATER_DROP", 39, fieload("WATER_DROP"), "droplet", 39, 8, new ParticleProperty[0]),
    ITEM_TAKE("ITEM_TAKE", 40, fieload("ITEM_TAKE"), "take", 40, 8, new ParticleProperty[0]),
    MOB_APPEARANCE("MOB_APPEARANCE", 41, fieload("MOB_APPEARANCE"), "mobappearance", 41, 8, new ParticleProperty[0]);

 */