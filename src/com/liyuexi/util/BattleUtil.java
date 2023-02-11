package com.liyuexi.util;

import com.liyuexi.GamePanel;
import com.liyuexi.beans.Monster;
import com.liyuexi.data.ImageData;
import com.liyuexi.data.MonsterData;
import com.liyuexi.data.MapData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * BattleUtil 工具类
 * <p>
 * 绘制战斗过程页面。（ 在玩家能击败怪物的情况下 ）
 *
 * @author ZYY
 * @since 2018-7-14
 */
public class BattleUtil {

    // 战斗页面文字的预设格式 Font
    private static final Font BATTLE_FONT = new Font("Serif", 0, 35);

    public static JLayeredPane battleLPane = new JLayeredPane();       // 战斗信息面板

    private JLabel battleBgLabel;
    private JLabel monsterImg;                      // 怪物图片
    private JLabel monster_hp = new JLabel();       // 怪物生命值
    private JLabel monster_attack = new JLabel();   // 怪物攻击力
    private JLabel monster_defend = new JLabel();   // 怪物防御力
    private JLabel player_hp = new JLabel();        // 玩家生命值
    private JLabel player_attack = new JLabel();    // 玩家攻击力
    private JLabel player_defend = new JLabel();    // 玩家防御力

    private Monster monsterBean;
    private int hp;
    private int attack;
    private int defend;

    /**
     * @param id 怪物id
     * @param x  怪物x坐标
     * @param y  怪物y坐标
     */
    public BattleUtil(int id, int x, int y) {

        monsterBean = MonsterData.monsterMap.get(id);
        hp = monsterBean.getHp();
        attack = monsterBean.getAttack();
        defend = monsterBean.getDefend();

        // 取图
        battleBgLabel = new JLabel(new ImageIcon(ImageData.battleBgImg));
        monsterImg = new JLabel(new ImageIcon(GamePanel.imgSource.get(id)));

        // 初始化 战斗信息面板
        battleLPane.setLayout(null);
        battleLPane.setBounds(27, GamePanel.GAME_PIX_72 * 2, 1242, 541);
        battleBgLabel.setBounds(0, 0, 1242, 541);
        battleLPane.add(battleBgLabel, 1, 0);
        battleLPane.setOpaque(true);
        battleLPane.setVisible(false);

        //
        int tmp = -50;
        monster_hp.setBounds(400, 37 + tmp, 300, 300);
        monster_hp.setFont(BATTLE_FONT);
        monster_hp.setForeground(Color.WHITE);

        monster_attack.setBounds(400, 157 + tmp, 300, 300);
        monster_attack.setFont(BATTLE_FONT);
        monster_attack.setForeground(Color.WHITE);

        monster_defend.setBounds(400, 291 + tmp, 300, 300);
        monster_defend.setFont(BATTLE_FONT);
        monster_defend.setForeground(Color.WHITE);

        player_hp.setBounds(785, 37 + tmp, 300, 300);
        player_hp.setFont(BATTLE_FONT);
        player_hp.setForeground(Color.WHITE);

        player_attack.setBounds(785, 157 + tmp, 300, 300);
        player_attack.setFont(BATTLE_FONT);
        player_attack.setForeground(Color.WHITE);

        player_defend.setBounds(785, 291 + tmp, 300, 300);
        player_defend.setFont(BATTLE_FONT);
        player_defend.setForeground(Color.WHITE);

        battleLPane.add(monster_hp, 2, 0);
        battleLPane.add(monster_attack, 3, 0);
        battleLPane.add(monster_defend, 4, 0);
        battleLPane.add(player_hp, 5, 0);
        battleLPane.add(player_attack, 6, 0);
        battleLPane.add(player_defend, 7, 0);

        monsterImg.setBounds(100, 120, 72, 72);
        battleLPane.add(monsterImg, 8, 0);

        monster_hp.setText(hp + "");
        monster_attack.setText(attack + "");
        monster_defend.setText(defend + "");

        player_hp.setText(GamePanel.player.getHp() + "");
        player_attack.setText(GamePanel.player.getAttack() + "");
        player_defend.setText(GamePanel.player.getDefend() + "");
        battleLPane.setVisible(true);
        GamePanel.inConversation = true;
        Timer bFrame = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ex) {
                attack(/*monsterBean?*/);
                monster_hp.setText(hp + "");
                player_hp.setText(GamePanel.player.getHp() + "");
                GamePanel.repaint();
                if (hp <= 0) {
                    battleLPane.setVisible(false);
                    GamePanel.inConversation = false;

                    GamePanel.player.setExp(GamePanel.player.getExp() + monsterBean.getExp());//exp += monsterBean.getExp();
                    GamePanel.player.setMoney(GamePanel.player.getMoney() + monsterBean.getMoney());// += monsterBean.getMoney();
                    MsgUtil.displayMessage("获得金币数 " + monsterBean.getExp() + " 经验值 " + monsterBean.getMoney() + " ！");
                    battleLPane.remove(monsterImg);
                    battleLPane.remove(monster_hp);
                    battleLPane.remove(monster_attack);
                    battleLPane.remove(monster_defend);
                    battleLPane.remove(player_hp);
                    battleLPane.remove(player_attack);
                    battleLPane.remove(player_defend);

                    MapData.LvMap[GamePanel.currentFloor][y][x] = 0;
                    GamePanel.player.setPosXAndPosY(x, y);
                    ((Timer) ex.getSource()).stop();
                }
            }
        });
        bFrame.start();
    }

    private void attack(/*MonsterBean e*/) {
        if (GamePanel.player.getAttack() > defend) {
            hp = hp - GamePanel.player.getAttack() + defend;
        }
        if (hp <= 0) return;
        if (attack > GamePanel.player.getDefend()) {
            GamePanel.player.setHp(GamePanel.player.getHp() - attack + GamePanel.player.getDefend()); //= hp - e.getAttack() + defend;
        }
        if (GamePanel.player.getAttack() < defend && attack < GamePanel.player.getDefend()) return;
    }
}
