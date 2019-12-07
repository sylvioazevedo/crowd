/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sylvioazevedo.crowd.elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author alves
 */
public class Claimer implements Element, Comparable<Claimer> {

    // Control properties
    private int counter = 0;
    private int vertical;
    private long claimInterval;
    private volatile boolean isClaiming;
    private volatile boolean direction;
    private volatile int slide = 0;
    private volatile int position = 0;
    

    // Image buffers
    private BufferedImage spriteClaimer;
    private BufferedImage spriteDir;
    private BufferedImage spriteEsq;

    @Override
    public void init() throws IOException {
        this.claimInterval =  randomNumber(10, 50);
        this.vertical = (int) randomNumber(180, 320);
        this.spriteClaimer = ImageIO.read(getClass().getResource("/br/com/sylvioazevedo/crowd/images/claimer.gif"));
        this.spriteDir = ImageIO.read(getClass().getResource("/br/com/sylvioazevedo/crowd/images/walking_dir.gif"));
        this.spriteEsq = ImageIO.read(getClass().getResource("/br/com/sylvioazevedo/crowd/images/walking_esq.gif"));
    }

    @Override
    public void draw(Graphics2D plane) {

        if (this.isClaiming) {
            plane.drawImage(spriteClaimer, 0 + position, vertical, 50 + position, 70 + vertical, 0 + (50 * slide), 0, 50 + (50 * slide), 70, null);
        }
        else{
            if (this.direction) {
                plane.drawImage(spriteEsq, 0 + position, vertical, 50 + position, 70 + vertical, 0 + (50 * slide), 0, 50 + (50 * slide), 70, null);
            } else {
                plane.drawImage(spriteDir, 0 + position, vertical, 50 + position, 70 + vertical, 0 + (50 * slide), 0, 50 + (50 * slide), 70, null);
            }
        }
    }

    @Override
    public void step() {
        
        if(counter==this.claimInterval) {
            isClaiming = !isClaiming;
            this.slide = 0;
            counter = 0;
        }
        else
            counter++;

        // If the claimer is claimming
        if (this.isClaiming) {

            if (this.slide < 1) {
                this.slide++;
            } else {
                this.slide = 0;
            }
            return;
        }

        if (this.slide < 3) {
            this.slide++;
        } else {
            this.slide = 0;
        }

        // If the claimer is walking
        if (direction) {
            if (this.position > 0) {
                this.position -= 10;
            } else {
                direction = false;
            }
        } else {
            if (this.position < 750) {
                this.position += 10;
            } else {
                direction = true;
            }
        }
    }

    private static long randomNumber(int min, int max) {
        return Math.round((Math.random() * (max - min)) + min);
    }

    @Override
    public int compareTo(Claimer o) {
        return this.vertical == o.vertical? 0: this.vertical < o.vertical? -1: 1;
    }
}
