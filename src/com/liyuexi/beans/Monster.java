package com.liyuexi.beans;

public class Monster {
    @Deprecated
    public int id; //怪物id
    private String name; //怪物名称

    private int hp; //生命值
    private int attack; //攻击力
    private int defend; //防御力
    private int exp; //经验值
    private int money; //金钱

    public Monster(int id, int hp, int attack, int defend, int money, int exp, String name) {
        this.id = id;
        this.hp = hp;
        this.attack = attack;
        this.defend = defend;
        this.money = money;
        this.exp = exp;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
