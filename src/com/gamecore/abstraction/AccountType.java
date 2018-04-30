package com.gamecore.abstraction;

/**
 * Created by user on 2018-01-10.
 */
public enum AccountType {

    Streamer(){
        @Override
        public String getName() {
            return "§d스트리머";
        }
    }
    , Premium(){
        @Override
        public String getName() {
            return "§b프리미엄";
        }
    }
    , Normal(){
        @Override
        public String getName() {
            return "§7일반";
        }
    }, Old(){
        @Override
        public String getName() {
            return "§6숙련자";
        }
    }, Admin(){
        @Override
        public String getName() {
            return "§c관리자";
        }
    };

    AccountType(){}

    public abstract String getName();

}
