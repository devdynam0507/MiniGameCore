package com.gamecore.modules;

import com.gamecore.abstraction.Game;

/**
 * Created by user on 2017-12-27.
 */
public abstract class GameModule {
    /** 서버 미니게임의 관리를 위해 게임들을 모두 모듈화 시킴*/
    private String name;
    private Game game;
    private int status = 0;

    protected GameModule(String name){
        this.name = name;
    }

    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public abstract boolean load();

    public String getName(){
        return this.name;
    }

}
