package com.gamecore.modules;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 2017-12-29.
 */
public class PluginModuleManager {

    private static PluginModuleManager inst;
    private Set<PluginModule> modules;

    private PluginModuleManager(){
        modules = new HashSet<>();
    }

    /** 미니게임 부가서비스 플러그인 모듈매니저를 가져옵니다. */
    public static PluginModuleManager module(){
        if(inst == null) inst = new PluginModuleManager();
        return inst;
    }

    /** 미니게임 부가서비스 플러그인 모듈을 등록시킵니다. */
    public void register(PluginModule module){
        if(module instanceof PluginModule){
            this.load(module);
        }
    }

    /** 미니게임 부가서비스 플러그인 모듈 등록을 해제시킵니다. */
    public void unregister(PluginModule module){
        if(module instanceof PluginModule && this.isRegistered(module)){
            this.unload(module);
        }
    }

    /** 미니게임 부가서비스 플러그인 모듈을 언로드 합니다. */
    private void unload(PluginModule module){
        if(module.getStatus() == 1){
            module.setStatus(0);
            module.getPlugin().getPluginLoader().disablePlugin(module.getPlugin());
            this.modules.remove(module);
            System.out.println("[MINIGAMECORE-API] " + module.getPlugin().getName() + " unregistered");
        }
    }

    /** 미니게임 부가서비스 플러그인 모듈을 로드합니다. */
    private void load(PluginModule module){
        if(module.getStatus() == 0){
            boolean success = module.load();
            if(success) {
                module.setStatus(1);
                this.modules.add(module);
                module.getPlugin().getPluginLoader().enablePlugin(module.getPlugin());
                System.out.println("[MINIGAMECORE-API] " + module.getPlugin().getName() + " registered");
            }else {
                module.getPlugin().getPluginLoader().disablePlugin(module.getPlugin());
                System.out.println("Module registration failed - [" + module.getPlugin().getName() + "]");
            }
        }
    }

    /** 미니게임 부가서비스 플러그인 모듈이 등록되어있는지 확인합니다.
     * @return Is registered pluginmodule */
    public boolean isRegistered(PluginModule module){
        if(module instanceof PluginModule && modules.contains(module) && module.getStatus() == 1)
            return true;
        return false;
    }

    /** 등록된 부가서비스 모듈을 가져옵니다.
     * @param name
     * @return GameModule*/
    public PluginModule getModule(String name){
        for(PluginModule module : this.modules){
            if(module.getPlugin().getName().equals(name) && this.isRegistered(module))
                return module;
        }

        return null;
    }

    /** 모듈 등록을 전체해제 시킵니다. */
    public void unregisterAll(){
        for(PluginModule module : this.modules){
            if(this.isRegistered(module) && module.getStatus() == 1){
                this.unregister(module);
            }
        }
    }

}
