package com.gamecore.abstraction;

import com.gamecore.data.PlayerData;

import java.util.List;

/**
 * Created by user on 2018-01-10.
 */
public abstract class Account {

    protected AccountType type;
    protected PlayerData data;

    protected double uniMag;

    public Account(PlayerData data, AccountType type){
        this.data = data;
        this.type = type;
    }

    public abstract void join(); /** 서버 접속시 호출되는 메소드 */

    public AccountType getType(){
        return this.type;
    }

    public void setType(AccountType type){
        this.type = type;
    }

    public PlayerData getPlayerData(){
        return this.data;
    }

    public double getUniMag(){
        return this.uniMag;
    }

    protected String[] getPrintMsg(){
        String[] str = new String[]{" ", "§6§lUnis Game에 오신걸 환영합니다!", "§f§l현재 해당 계정의 등급은 "+ this.type.getName() + " §f§l입니다.", "§b§l현재 당신의 유니 배율은 §f" + this.uniMag + " §b§l입니다." , " ", "§a§l재밌는 서버플레이 하시길 바랍니다. §f(/서버)", " "};
        return str;
    }

}
