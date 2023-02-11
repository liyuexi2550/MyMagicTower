package com.liyuexi.beans;

import com.liyuexi.data.ImageData;

import java.awt.image.BufferedImage;

/**
 * @author liyuexi
 */
public class Player {
    private int hp; //生命值
    private int attack; //攻击力
    private int defend; //防御力

    private int level; //等级
    private int exp; //经验值
    private int money; //金钱

    private int yellowKeysNum; //黄钥匙数
    private int blueKeysNum; //蓝钥匙数
    private int redKeysNum; //红钥匙数

    private int toward; //当前朝向 0-上 1-右 2-下 3-左
    private int posX; //X 坐标值
    private int posY; //Y 坐标值


    public BufferedImage draw(){
        return ImageData.playerMap.get(toward);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getYellowKeysNum() {
        return yellowKeysNum;
    }

    public void setYellowKeysNum(int yellowKeysNum) {
        this.yellowKeysNum = yellowKeysNum;
    }

    public int getBlueKeysNum() {
        return blueKeysNum;
    }

    public void setBlueKeysNum(int blueKeysNum) {
        this.blueKeysNum = blueKeysNum;
    }

    public int getRedKeysNum() {
        return redKeysNum;
    }

    public void setRedKeysNum(int redKeysNum) {
        this.redKeysNum = redKeysNum;
    }

    public int getToward() {
        return toward;
    }

    public void setToward(int toward) {
        this.toward = toward;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosXAndPosY(int posX,int posY){
        this.posX = posX;
        this.posY = posY;
    }

}
