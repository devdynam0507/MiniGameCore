package com.gamecore.util;

import com.google.common.collect.Maps;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017-07-01.
 */
public class ItemSerialize {

    private ItemStack itemStack;
    private String serial;

    public ItemSerialize(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemSerialize(String serial){
        this.serial = serial;
    }

    public String serialize(){
        StringBuilder sb = new StringBuilder();
        ItemMeta meta = itemStack.getItemMeta();
        sb.append("ItemStack[").append("type=" + itemStack.getTypeId() + ",").
                append("durability=" + itemStack.getDurability() + ",").
                append("amount=" + this.itemStack.getAmount() + ",").
                append("name=" + (meta.getDisplayName() != null ? meta.getDisplayName() : "null") + ",").
                append("enchant={");
        if(meta.getEnchants() != null) {
            for (Map.Entry<Enchantment, Integer> enchants : meta.getEnchants().entrySet()) {
                sb.append("" + enchants.getKey().getId() + ":" + enchants.getValue() + "-");
            }
        }
        sb.append("},").append("lore={");
        if(meta.getLore() != null){
            for(String lore : meta.getLore()){
                if(lore == null){
                    sb.append(" " + ":");
                    continue;
                }
                sb.append(lore + ":");
            }
        }
        sb.append("}]");
        return sb.toString();
    }

    public ItemStack deserialize(){
        String split[] = serial.substring(serial.indexOf('[') + 1, serial.indexOf(']')).split(",");
        //0 = typeid , 1 = dura , 2 = amount , 3 = name , 4 = enchants, 5 = lores
        try{
            ItemStack itemStack = new ItemStack(Integer.parseInt(split[0].split("=")[1]));
            itemStack.setDurability(Short.parseShort(split[1].split("=")[1]));
            itemStack.setAmount(Integer.parseInt(split[2].split("=")[1]));
            ItemMeta meta = itemStack.getItemMeta();
            if(!split[3].split("=")[1].equals("null"))
                meta.setDisplayName(split[3].split("=")[1]);
            if(this.hasArray(split[5]))
                meta.setLore(this.parseLore(split[5].split("=")[1]));
            itemStack.setItemMeta(meta);
            if(this.hasArray(split[4])) {
                String data = split[4].split("=")[1];
                itemStack.addUnsafeEnchantments(this.parseEnchantments(data));
            }
            return itemStack;
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<Enchantment, Integer> parseEnchantments(String data){
        String enchantmentValue[] = data.substring(data.indexOf('{') + 1, data.indexOf('}')).split("-");
        HashMap<Enchantment, Integer> enchants = Maps.newHashMap();
        for(String value : enchantmentValue){
            int id = Integer.parseInt(value.split(":")[0]);
            int level = Integer.parseInt(value.split(":")[1]);
            Enchantment enchantment = Enchantment.getById(id);
            enchants.put(enchantment, level);
        }
        return enchants;
    }

    private List<String> parseLore(String data){
        String loreValue[] = data.substring(data.indexOf('{') + 1, data.indexOf('}')).split(":");
        return Arrays.asList(loreValue);
    }

    private boolean hasArray(String data){
        int index1 = data.indexOf('{'), index2 = data.indexOf('}');
        return index2 - index1 != 1;
    }

}
