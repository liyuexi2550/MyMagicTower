package com.liyuexi.data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class ImageData {
    private static final int PLAYER_UP = 0; //0代表角色向上
    private static final int PLAYER_RIGHT = 1; //1代表角色向右
    private static final int PLAYER_DOWN = 2; //2代表角色向下
    private static final int PLAYER_LEFT = 3; //3代表角色向左

    public static HashMap<Integer, BufferedImage> playerMap = new HashMap<>();

    static{
        try{
            playerMap.clear();
            playerMap.put(PLAYER_UP, ImageIO.read(new File(System.getProperty("user.dir") + "/res/player/up.png")));
            playerMap.put(PLAYER_RIGHT, ImageIO.read(new File(System.getProperty("user.dir") + "/res/player/right.png")));
            playerMap.put(PLAYER_DOWN, ImageIO.read(new File(System.getProperty("user.dir") + "/res/player/down.png")));
            playerMap.put(PLAYER_LEFT, ImageIO.read(new File(System.getProperty("user.dir") + "/res/player/left.png")));



        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
