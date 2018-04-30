package com.gamecore.data;

import com.effects.prefixsystem.Prefix;
import com.gamecore.MiniGame;
import com.gamecore.abstraction.*;
import com.gamecore.util.GuiManager;
import com.gamecore.util.ItemBuilder;
import com.mitem.itemtypes.ItemType;
import com.mitem.wrappers.*;
import com.mitem.wrappers.ItemWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PlayerData {

    /** 전체 유저데이터 */
    private static List<PlayerData> players = new ArrayList<>();

    /** 유저데이터를 가져옵니다.
     * @param name
     * @return PlayerData */
    public static PlayerData get(String name){
        for(PlayerData data : players){
            if(data.getName().equals(name))
                return data;
        }
        return new PlayerData(name).load();
    }

    private String name; /** 유저이름 */
    private DataManager manager; /** 유저 Config */
    private GameRoom joinedGameRoom; /** 유저가 속해있는 대기실 */
    private Money money; /** 유저의 돈 데이터 */
    private Account account; /** 유저 계정 데이터 */
    private Prefix prefix; /** 유저 칭호 데이터 */

    /** 유저가 가지고있는 아이템들 타입별 분류 HashMap */
    private HashMap<ItemType, List<ItemWrapper>> hasItems = new HashMap<>();

    private PlayerData(String name){
        this.name = name;
        this.manager = new DataManager(new File(MiniGame.dir, "players"), name + ".yml");
        players.add(this);
    }

    /** 유저의 Bukkit.Player 객체를 반환합니다.
     * @return Player */
    public Player getPlayer(){
        return Bukkit.getPlayer(this.name);
    }

    public String getName(){
        return this.name;
    }

    public DataManager getDataManager(){
        return this.manager;
    }

    /** 게임 대기실에 입장합니다. */
    public void joinGameRoom(GameRoom room){
        this.joinedGameRoom = room;
        if(room.getGame().getRunningType() == RunningType.RUNNING){
            return;
        }
        room.join(this);
    }

    /** 대기실에 입장중이라면 대기실에서 퇴장합니다. */
    public void quit(){
        if(this.joinedGameRoom != null){
            this.joinedGameRoom.quit(this);
            this.joinedGameRoom = null;
        }
    }

    /** 유저 데이터를 로드합니다. */
    public PlayerData load(){
        if(this.getDataManager().get("account") == null){
            this.getDataManager().set("account", "Normal");
            try{
                this.getDataManager().save();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(this.getDataManager().get("money") == null){
            this.getDataManager().set("money", 0);
            this.getDataManager().set("cash", 0);
            this.getDataManager().set("crystal", 0);
            this.money = new Money();
            this.money.money = 0;
            this.money.cash = 0;
            this.money.crystals = 0;
            try{
                this.getDataManager().save();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            this.money = new Money();
            this.money.money = this.getDataManager().getInt("money");
            this.money.crystals = this.getDataManager().getInt("crystal");
            this.money.cash = this.getDataManager().getInt("cash");
        }

        if(this.getDataManager().get("prefixies") == null){
            this.prefix = new Prefix(this, new ArrayList<>(), "");
        }else{
            this.prefix = new Prefix(this, this.getDataManager().getStringList("prefixies"), this.getDataManager().getString("prefix"));
        }

        ItemType[] typeValues = ItemType.values();
        for(ItemType type : typeValues){
            List<ItemWrapper> wrappers = new ArrayList<>();
            if(this.getDataManager().get(type.toString()) != null && this.getPlayer() != null){
                List<String> itemNames = this.getDataManager().getStringList(type.toString());

                for(String name : itemNames){
                    if(name == null) continue;
                    System.out.println(name + "," + type + "." + this.name);
                    ItemWrapper wrapper = ItemManager.getItem(type, name, this.getPlayer());
                    wrappers.add(wrapper);
                }
                this.hasItems.put(type, wrappers);
            }else{
                this.hasItems.put(type, wrappers);
            }
        }
        return this;
    }

//=================================================================================================================================================================

    public List<ItemWrapper> getItems(ItemType type)
    {
        return this.hasItems.get(type);
    }

    /** 유저 계정에 아이템을 추가합니다. */
    public void addItem(ItemType type, ItemWrapper item){
        List<ItemWrapper> list = this.hasItems.get(type);
        if(type == ItemType.NONE || type == ItemType.PREFIX)
            return;
        for(ItemWrapper wrap : list){
            if(item != null && wrap.name.equals(item.name)){
                return;
            }
        }

        this.hasItems.get(type).add(item);
        this.saveItem(type);
    }

    /** 유저의 아이템을 삭제합니다. */
    public void remove(ItemTags tag) throws IOException, InvalidConfigurationException
    {
        if(this.getPlayer() == null)
        {
            List<String> list = this.manager.getStringList(tag.itemType.toString());
            list.remove(tag.name);
            this.manager.save();
            return;
        }

        List<ItemWrapper> wrappers = this.hasItems.get(tag.itemType);
        if(wrappers == null || wrappers.size() == 0)
            return;
        Iterator<ItemWrapper> iter = wrappers.iterator();
        while(iter.hasNext())
        {
            ItemWrapper i = iter.next();
            if (i.name.equals(tag.name) || i.name.equals(tag.cname))
            {
                System.out.println("deleted");
                iter.remove();
                break;
            }
        }
        this.hasItems.put(tag.itemType,  wrappers);
        this.saveItem(tag.itemType);
    }

    /** 아이템 데이터를 저장합니다. */
    private void saveItem(ItemType type){
        List<ItemWrapper> wrappers = this.hasItems.get(type);
        List<String> stringNameFm =  new ArrayList<>();
        for(ItemWrapper wrapper : wrappers){
            stringNameFm.add(wrapper.name);
        }

        this.getDataManager().set(type.toString(), stringNameFm);
        try{
            this.getDataManager().save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//=================================================================================================================================================================

    public Money getMoneys(){
        return this.money;
    }

    public void addMoney(int money){
        this.money.money += money;
        this.saveMoneys();
    }

    public void addCrystal(int crystal){
        this.money.crystals += crystal;
        this.saveMoneys();
    }

    public void addCash(int cash){
        this.money.cash += cash;
        this.saveMoneys();
    }

    /** 돈 데이터를 저장합니다. */
    public void saveMoneys(){
        this.getDataManager().set("money", this.money.money);
        this.getDataManager().set("cash", this.money.cash);
        this.getDataManager().set("crystal", this.money.crystals);

        try{
            this.getDataManager().save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//=================================================================================================================================================================

    /** 정보창을 엽니다. */
    public void openInfomation(){
        GuiManager manager = new GuiManager(9, "§a내 정보");
        manager.setItem(2 , new ItemBuilder(Material.EMERALD).addLore("§f- §7" + this.money.money).setDisplayName("§6유니(Uni)").setAmounts(1).build()).setItem(4, new ItemBuilder(Material.DIAMOND).setAmounts(1).setDisplayName("§b캐쉬").setAmounts(1).addLore("§f- §7" + money.cash).build())
                .setItem(6, new ItemBuilder(Material.NETHER_STAR).setAmounts(1).setDisplayName("§a크리스탈").addLore("§f- §7" + this.money.crystals).build());

        this.getPlayer().openInventory(manager.getInventory());
    }

//=================================================================================================================================================================

    /** 계정 타입을 반환합니다. */
    public AccountType getType(){
        return AccountType.valueOf(this.getDataManager().getString("account"));
    }

    public void setAccount(Account account){
        this.account = account;
        this.getDataManager().set("account", account.getType().toString());

        try{
            this.getDataManager().save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /** 계정 데이터를 반환합니다. */
    public Account getAccount(){
        return this.account;
    }

//=================================================================================================================================================================

    /** 칭호 데이터를 설정합니다. */
    public void setPrefix(Prefix prefix){
        this.prefix = prefix;
    }

    /** 칭호를 추가합니다. */
    public void addPrefixWrap(String prefix, String color){
        this.prefix.add(prefix, color);
    }

    /** 칭호 데이터를 반환합니다. */
    public Prefix getPrefix(){
        return this.prefix;
    }

//=================================================================================================================================================================

}
