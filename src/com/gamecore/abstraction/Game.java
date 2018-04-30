package com.gamecore.abstraction;

import com.gamecore.data.DataManager;
import com.gamecore.data.PlayerData;
import com.gamecore.modules.GameModule;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Game extends GameModule{

    /** 복구할 블럭들을 담은 HashMap */
    private HashMap<Location, Object[]> recoverTargetBlocks = new HashMap<>();

    protected boolean registered; /** 모듈 등록여부 */
    public RunningType run; /** 게임 진행 여부 */
    protected List<PlayerData> players = new ArrayList<>(); /** 현재 이 게임에있는 플레이어들 List */
    protected DataManager gameDataManager; /** Game Config */
    protected String name; /** 게임 이름 */

    protected Game(String name){
        super(name);
        this.name = name;
    }

    public abstract void start(List<PlayerData> players); /** 게임 시작 */
    public abstract void stop(); /** 게임 종료 */
    public abstract RunningType getRunningType();
    public abstract DataManager getGameDataManager();
    public abstract void recover(); /** 게임 맵 복구 */
    public abstract void teamDesignate(); /** 팀 추첨 */

    public abstract String getCustomName(); /** GUI에 표시될 게임 이름 */
    public abstract List<String> getGameLore(); /** GUI에 표시될 게임 설명 */

    public abstract String getName(); /** 게임 이름 반환 */

    /** 모듈 반환 */
    public GameModule getModule(){
        return this;
    }

}
