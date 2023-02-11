package com.liyuexi.beans;

import com.liyuexi.GamePanel;
import com.liyuexi.data.ImageData;

import java.awt.image.BufferedImage;

public class Dialogues {
    private int id;

    public Dialogues(int id){
        this.id = id;

        String[] messages;
        BufferedImage[] characters = new BufferedImage[50];
        int[] w = new int[50];
        int[] h = new int[50];
        for(int i = 0;i < 50; i++){
            w[i] = 540;
            h[i] = 170;
        }
        for(int i = 0; i < characters.length; i++){
            if(i % 2 == 0){
                characters[i] = ImageData.playerMap.get(2);
            }else{
                characters[i] = GamePanel.imgSource.get(id);
            }
        }
    }
}
