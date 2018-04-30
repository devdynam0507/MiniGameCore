package com.gamecore.modules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 2017-12-27.
 */
public class GameModuleManager {

    private static GameModuleManager inst;
    private Set<GameModule> modules;

    private GameModuleManager(){
        modules = new HashSet<>();
    }

    /** 모듈 매니저를 받아옵니다. */
    public static GameModuleManager module(){
        if(inst == null) inst = new GameModuleManager();
        return inst;
    }

    /** 게임 모듈을 등록시킵니다. */
    public void register(GameModule module){
        if(module instanceof GameModule){
            this.load(module);
        }
    }

    /** 게임 모듈 등록을 해제시킵니다. */
    public void unregister(GameModule module){
        if(module instanceof GameModule && this.isRegistered(module)){
            this.unload(module);
        }
    }

    /** 게임 모듈을 언로드 합니다. */
    private void unload(GameModule module){
        if(module.getStatus() == 1){
            module.setStatus(0);
            this.modules.remove(module);
            System.out.println("[MINIGAMECORE-API] " + module.getName() + " unregistered");
        }
    }

    /** 게임 모듈을 로드합니다. */
    private void load(GameModule module){
        if(module.getStatus() == 0){
            boolean success = module.load();
            if(success) {
                module.setStatus(1);
                this.modules.add(module);
                System.out.println("[MINIGAMECORE-API] " + module.getName() + " registered");
            }else
                System.out.println("Module registration failed");
        }
    }

    /** 게임모듈이 등록되었는지 확인합니다.
     * @return Is registered gamemodule */
    public boolean isRegistered(GameModule module){
        if(module instanceof GameModule && modules.contains(module) && module.getStatus() == 1)
            return true;
        return false;
    }

    /** 등록된 모듈을 가져옵니다.
     * @param name
     * @return GameModule*/
    public GameModule getModule(String name){
        for(GameModule module : this.modules){
            if(module.getName().equals(name) && this.isRegistered(module))
                return module;
        }

        return null;
    }

    /** 모들 등록을 전체해제 합니다. */
    public void unregisterAll(){
        for(GameModule module : this.modules){
            if(this.isRegistered(module) && module.getStatus() == 1){
                this.unregister(module);
            }
        }
    }

    @Deprecated
    public List<GameModule> getModules(){
        List<GameModule> moduels = new ArrayList<>();
        for(GameModule module : this.modules){
            moduels.add(module);
        }
        return moduels;
    }

}
