package com.liyuexi.data;

import com.liyuexi.beans.Monster;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.imageio.ImageIO;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MusicData {
    public static HashMap<Integer, String> musicMap = new HashMap<>();
    static{
        try{
            musicMap.clear();
            musicMap.put(0, System.getProperty("user.dir") +  "/res/music/获得道具.mp3");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void play(int id) {
        BufferedInputStream buffer;
        try {
            buffer = new BufferedInputStream(new FileInputStream(musicMap.get(id)));//创建缓冲输入流对象
            new Thread(
                    () -> {
                        Player player;
                        try {
                            assert false;
                            player = new Player(buffer);//创建Player的对象
                            player.play();
                        } catch (JavaLayerException e) {
                            e.printStackTrace();
                        }
                    }

            ).start();  //开启线程
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
