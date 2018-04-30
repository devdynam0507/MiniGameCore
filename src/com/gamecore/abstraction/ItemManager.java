package com.gamecore.abstraction;

import com.effects.particle.ParticleManager;
import com.effects.pet.PetManager;
import com.effects.prefixsystem.Prefix;
import com.gamecore.data.PlayerData;
import com.mitem.items.PrefixWrapper;
import com.mitem.itemtypes.ItemObjectType;
import com.mitem.itemtypes.ItemType;
import com.mitem.wrappers.ItemWrapper;
import org.bukkit.entity.Player;

/**
 * Created by user on 2018-01-26.
 */
public class ItemManager
{

        public static ItemWrapper getItem(ItemType type, String itemName, Player player){
            switch (type){
                case PET:
                    return (ItemWrapper) PetManager.manager().getPetInstance(player, itemName, ItemObjectType
                            .NO_TermItem, 0);
                case PARTICLE:
                    return ParticleManager.manager().getParticleEffects(itemName, player,
                            ItemObjectType.TermItem, 1);
            }
            return null;
        }

        public static ItemWrapper getRandomItem(ItemType type, Player player){
            switch (type){
                case PET:
                    String[] name = new String[]{ "치킨", "마그마 큐브", "머쉬룸", "오셀롯", "돼지", "양", "슬라임", "주민", "좀비"};
                    return (ItemWrapper) PetManager.manager().getPetInstance(player, name[(int) (Math.random() * name
                            .length)], ItemObjectType.NO_TermItem, 0);
                case PARTICLE:
                    String pType = ParticleManager.types[(int) (Math.random() * ParticleManager.types.length)];
                    return ParticleManager.manager().getParticleEffects(pType, player, ItemObjectType.NO_TermItem, 0);
                case NONE:
                    return null;
                case PREFIX:
                    String pick[] = Prefix.s_prefixes.get((int) (Math.random() * Prefix.s_prefixes.size())).split("-");
                    return new PrefixWrapper(pick[0], pick[1], PlayerData.get(player.getName()), 0, player.getName());
            }
            return null;
        }
}
