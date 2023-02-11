package com.liyuexi;


import com.liyuexi.beans.Dialogues;
import com.liyuexi.beans.Item;
import com.liyuexi.beans.Player;
import com.liyuexi.data.ImageData;
import com.liyuexi.data.MapData;
import com.liyuexi.data.MonsterData;
import com.liyuexi.data.MusicData;
import com.liyuexi.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_L;

public class GamePanel extends JPanel {
    public static final int GAME_PIX_54 = 54;
    public static final int GAME_PIX_72 = 72;
    public static HashMap<Integer, BufferedImage> imgSource = ImageData.playerMap;

    public static Player player = new Player();
    public static JLabel timeLabel;
    public static int gameMin = 0;
    public static double gameSec = 0;

    public static boolean inConversation = false;   // 允许键盘交互
    public static boolean talked = false;
    public static int currentFloor = 0;     // 当前楼层
    public static int maxFloor = 0;         // 最大楼层
    public GamePanel(){
        setPreferredSize(new Dimension(GAME_PIX_72 * 18, GAME_PIX_72 * 13));

        add(ForecastUtil.forecastLPane);
        add(JumpUtil.jumpLPane);
        add(BattleUtil.battleLPane);
        add(MsgUtil.msgLPane);
        add(timeLabel);

        //定时刷新场景
        new Timer(500, new ActionListener() {
            boolean change = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                gameSec += 0.5;  // 频率为 0.5s
                if (gameSec == 60) {
                    gameSec = 0;
                    gameMin++;
                }
                timeLabel.setText(" 游戏时间：" + gameMin + " 分 " + (int) gameSec + " 秒");
                if (change) {
                    change = false;
                    imgSource = ImageData.imagesMap0;
                } else {
                    change = true;
                    imgSource = ImageData.imagesMap1;
                }
                repaint();
            }
        }).start();

        //监听键盘事件（响应玩家操作）
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!inConversation)
                    switch (e.getKeyCode()) {

                        case VK_DOWN:   // 键盘 ↓
                            if (player.getPosY() + 1 < 11 && player.getPosY() + 1 >= 0) {
                                player.setToward(2);
                                interaction(player.getPosX(), player.getPosY() + 1);
                                repaint();
                            }
                            break;
                        case VK_RIGHT:  // 键盘 →
                            if (player.getPosX() + 1 < 11 && player.getPosX() + 1 >= 0) {
                                player.setToward(1);
                                interaction(player.getPosX() + 1, player.getPosY());
                                repaint();
                            }
                            break;
                        case VK_UP:     // 键盘 ↑
                            if (player.getPosY() - 1 < 11 && player.getPosY() - 1 >= 0) {
                                player.setToward(0);
                                interaction(player.getPosX(), player.getPosY() - 1);
                                repaint();
                            }
                            break;
                        case VK_LEFT:   // 键盘 ←
                            if (player.getPosX() - 1 < 11 && player.getPosX() - 1 >= 0) {
                                player.setToward(3);
                                interaction(player.getPosX() - 1, player.getPosY());
                                repaint();
                            }
                            break;
                        case VK_J:      // 键盘 J
                            if (Item.isHasJump) {
                                JumpUtil.displayJump();
                            }
                            break;
                        case VK_L:      // 键盘 L
                            if (Item.isHasForecast) {
                                ForecastUtil.displayForecast();
                            }
                            break;
                    }
                else if (e.getKeyCode() == e.VK_L)//bug
                {
                    inConversation = false;
                    ForecastUtil.forecastLPane.removeAll();
                    ForecastUtil.forecastLPane.setVisible(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            

        });
    }

    /**
     * 核心交互逻辑
     * @param x
     * @param y
     */
    public static void interaction(int x,int y){
        int id = MapData.LvMap[currentFloor][y][x];
        switch (id) {
            case 0:     // 玩家移动
                player.setPosXAndPosY(x, y);
                break;
            case 1:     // 砖墙
                break;
            case 2:     // 黄门
                if (player.getYellowKeysNum() > 0) {
                    MapData.LvMap[currentFloor][y][x] = 0;
                    player.setYellowKeysNum(player.getYellowKeysNum() - 1);
                    player.setPosXAndPosY(x, y);
                }
                break;
            case 3:     // 蓝门
                if (player.getBlueKeysNum() > 0) {
                    MapData.LvMap[currentFloor][y][x] = 0;
                    player.setBlueKeysNum(player.getBlueKeysNum() - 1);
                    player.setPosXAndPosY(x, y);
                }
                break;
            case 4:     // 红门
                if (player.getRedKeysNum() > 0) {
                    MapData.LvMap[currentFloor][y][x] = 0;
                    player.setRedKeysNum(player.getRedKeysNum() - 1);
                    player.setPosXAndPosY(x, y);
                }
                break;
            case 5:     // 石块
                break;
            case 6:     // [道具] 黄钥匙
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setYellowKeysNum(player.getYellowKeysNum() + 1);
                MsgUtil.displayMessage("得到一把 黄钥匙 ！");
                MusicData.play(0);
                break;
            case 7:     // [道具] 蓝钥匙
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setBlueKeysNum(player.getBlueKeysNum() + 1);
                MsgUtil.displayMessage("得到一把 蓝钥匙 ！");
                break;
            case 8:     // [道具] 红钥匙
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setRedKeysNum(player.getRedKeysNum() + 1);
                MsgUtil.displayMessage("得到一把 红钥匙 ！");
                break;
            case 9:     // [道具] 蓝宝石
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setDefend(player.getDefend() + 3);
                MsgUtil.displayMessage("得到一个蓝宝石 防御力加 3 ！");
                break;
            case 10:    // [道具] 红宝石
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setAttack(player.getAttack() + 3);
                MsgUtil.displayMessage("得到一个红宝石 攻击力加 3 ！");
                break;
            case 11:    // [道具] 红药水
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setHp(player.getHp() + 200);
                MsgUtil.displayMessage("得到一个小血瓶 生命加 200 ！");
                break;
            case 12:    // [道具] 蓝药水
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setHp(player.getHp() + 500);
                MsgUtil.displayMessage("得到一个大血瓶 生命加 500 ！");
                break;
            case 13:    // 上楼
                currentFloor++;
                maxFloor = Math.max(maxFloor, currentFloor);
                player.setPosXAndPosY(MapData.initPos[currentFloor][0], MapData.initPos[currentFloor][1]);
                break;
            case 14:    // 下楼
                currentFloor--;
                player.setPosXAndPosY(MapData.finPos[currentFloor][0], MapData.finPos[currentFloor][1]);
                break;
            case 15:    // 不可通过的护栏
                break;
            case 19:    // 火海
                break;
            case 20:    // 星空
                break;
            case 22:    // 商店
                if (currentFloor == 3) {
                    ShopUtil.shop(0);
                } else if (currentFloor == 11) {
                    ShopUtil.shop(3);
                }
                break;
            case 24:    // [对话] 仙子
                new Dialogues(id);
                break;
            case 25:    // [对话] 小偷
            case 26:    // [对话] 老人
            case 27:    // [对话] 商人
            case 28:    // [对话] 公主
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                break;
            case 30:    // [道具] 小飞羽
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setLevel(player.getLevel() + 1);
                player.setHp(player.getHp() + 1000);
                player.setAttack(player.getAttack() + 7);
                player.setDefend(player.getDefend() + 7);
                MsgUtil.displayMessage("得到 小飞羽 等级提升一级 ！");
                break;
            case 31:    // [道具] 大飞羽
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setLevel(player.getLevel() + 3);
                player.setHp(player.getHp() + 3000);
                player.setAttack(player.getAttack() + 21);
                player.setDefend(player.getDefend() + 21);
                MsgUtil.displayMessage("得到 大飞羽 等级提升三级 ！");
                break;
            case 32:    // [宝物] 幸运十字架
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                Item.isHasCross = true;
                MsgUtil.displayMessage("【幸运十字架】 把它交给序章中的仙子，可以将自身的所有能力提升一些（攻击、防御和生命值）。");
                break;
            case 33:    // [宝物] 圣水瓶
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setHp(player.getHp() * 2);
                MsgUtil.displayMessage("【圣水瓶】 它可以将你的体质增加一倍（生命值加倍）。");
                break;
            case 34:    // [宝物] 圣光徽
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                Item.isHasForecast = true;
                MsgUtil.displayMessage("【圣光徽】 按 L 键使用 查看怪物的基本情况。");
                break;
            case 35:    // [宝物] 风之罗盘
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                Item.isHasJump = true;
                MsgUtil.displayMessage("【风之罗盘】 按 J 键使用 在已经走过的楼层间进行跳跃。");
                break;
            case 36:    // [道具] 钥匙盒
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setYellowKeysNum(player.getYellowKeysNum() + 1);
                player.setBlueKeysNum(player.getBlueKeysNum() + 1);
                player.setRedKeysNum(player.getRedKeysNum() + 1);
                MsgUtil.displayMessage("得到 钥匙盒 各种钥匙数加 1 ！");
                break;
            case 38:    // [宝物] 星光神榔
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                Item.isHasHammer = true;
                MsgUtil.displayMessage("【星光神榔】 把它交给第四层的小偷，小偷便会用它打开第十八层的隐藏地面（你就可以救出公主了）。");
                break;
            case 39:    // [道具] 金块
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setMoney(player.getMoney() + 300);
                MsgUtil.displayMessage("得到 金块 金币数加 300 ！");
                break;
            case 40:    // [怪物 monster]
            case 41:    // [怪物 monster]
            case 42:    // [怪物 monster]
            case 43:    // [怪物 monster]
            case 44:    // [怪物 monster]
            case 45:    // [怪物 monster]
            case 46:    // [怪物 monster]
            case 47:    // [怪物 monster]
            case 48:    // [怪物 monster]
            case 49:    // [怪物 monster]
            case 50:    // [怪物 monster]
            case 51:    // [怪物 monster]
            case 52:    // [怪物 monster]
            case 53:    // [怪物 monster]
            case 54:    // [怪物 monster]
            case 55:    // [怪物 monster]
            case 56:    // [怪物 monster]
            case 57:    // [怪物 monster]
            case 58:    // [怪物 monster]
            case 59:    // [怪物 monster]
            case 60:    // [怪物 monster]
            case 61:    // [怪物 monster]
            case 62:    // [怪物 monster]
            case 63:    // [怪物 monster]
            case 64:    // [怪物 monster]
            case 65:    // [怪物 monster]
            case 66:    // [怪物 monster]
            case 67:    // [怪物 monster]
            case 68:    // [怪物 monster]
            case 69:    // [怪物 monster]
            case 70:    // [怪物 monster]
                if (ForecastUtil.forecast(MonsterData.monsterMap.get(id)).equals("???")
                        || Integer.parseInt(ForecastUtil.forecast(MonsterData.monsterMap.get(id))) >= player.getHp()) {
                    return;
                } else {
                    new BattleUtil(id, x, y);
                }
                break;
            case 71:    // [宝物] 铁剑
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setAttack(player.getAttack() + 10);
                MsgUtil.displayMessage("得到 铁剑 攻击加 10 ！");
                break;
            case 73:    // [宝物] 钢剑
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setAttack(player.getAttack() + 30);
                MsgUtil.displayMessage("得到 钢剑 攻击加 30 ！");
                break;
            case 75:    // [宝物] 圣光剑
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setAttack(player.getAttack() + 120);
                MsgUtil.displayMessage("得到 圣光剑 攻击加 120 ！");
                break;
            case 76:    // [宝物]
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setDefend(player.getDefend() + 10);
                MsgUtil.displayMessage("得到 铁盾 防御加 10 ！");
                break;
            case 78:    // [宝物] 钢盾
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setDefend(player.getDefend() + 30);
                MsgUtil.displayMessage("得到 钢盾 防御加 30 ！");
                break;
            case 80:    // [宝物] 星光盾
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                player.setDefend(player.getDefend() + 120);
                MsgUtil.displayMessage("得到 星光盾 防御加 120 ！");
                break;
            case 115:   // 可通过的护栏
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                break;
            case 119:   // 同 case 1:
            case 129:   // 同 case 1:
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                break;
            case 188:
                if (ForecastUtil.forecast(MonsterData.monsterMap.get(id)).equals("???")
                        || Integer.parseInt(ForecastUtil.forecast(MonsterData.monsterMap.get(id))) >= player.getHp()) {
                    return;
                } else {
                    new BattleUtil(id, x, y);
                }
                break;
            case 202:   // [宝物] 炎之灵杖
                MsgUtil.displayMessage("得到 炎之灵杖");
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                break;
            case 203:   // [宝物] 心之灵杖
                MsgUtil.displayMessage("得到 心之灵杖");
                MapData.LvMap[currentFloor][y][x] = 0;
                player.setPosXAndPosY(x, y);
                break;
            case 301:   // 22层 到 24层
                currentFloor += 2;
                player.setPosXAndPosY(5, 1);
                break;
            case 302:   // 22层 到 25层
                currentFloor += 3;
                player.setPosXAndPosY(1, 5);
                break;
            case 303:   // 24层 到 22层
                currentFloor -= 2;
                player.setPosXAndPosY(5, 9);
                break;
            case 304:   // 25层 到 22层
                currentFloor -= 3;
                player.setPosXAndPosY(9, 5);
                break;
            case 305:   // 24层 到 26层
                currentFloor += 2;
                player.setPosXAndPosY(5, 10);
                break;
        }
    }
}
