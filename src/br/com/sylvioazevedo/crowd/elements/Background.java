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
public class Background implements Element {
    
    private BufferedImage background;

    public void init() throws IOException {

        // Loads background image.
        this.background = ImageIO.read(getClass().getResource("/br/com/sylvioazevedo/crowd/images/bg.gif"));
    }

    public void draw(Graphics2D plane) {
        
        // Put image on screen.
        plane.drawImage(this.background, 0, 0, null);
    }

    public void step() {
        // No steps are need for background.
    }
}
