/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sylvioazevedo.crowd;

import br.com.sylvioazevedo.crowd.elements.Background;
import br.com.sylvioazevedo.crowd.elements.Claimer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JFrame;

import br.com.sylvioazevedo.crowd.view.Main;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;


/**
 *
 * @author alves
 */
public class CrowdTest extends JApplet {

    // Propriedades    
    private Graphics2D    backBuffer;
    private BufferedImage backImage;

    // Elementos
    public Background    bg;
    public List<Claimer> claimers;

    private static final  Object timeControl = new Object();
    private boolean write;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        
        this.setFocusable(true);

        // Inicializa elementos
        this.bg = new Background();

        try {
            this.bg.init();
        }
        catch (IOException ex) {
            Logger.getLogger(CrowdTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.claimers = new ArrayList<Claimer>();
        
        // Put listener
        this.addKeyListener(new CrowdTest.DefaultKeyListener());

        // Init time line
        new Thread(new CrowdTest.TimeLine(this)).start();
    }
    
    
    

    @Override
    public void paint(Graphics g) {

        // Inicializa o backbuffer se necessário.        
        if(this.backBuffer==null)   {
            backImage = new BufferedImage(800, 600, Transparency.BITMASK);
            this.backBuffer = backImage.createGraphics();
        }

        // Monta o fundo
        this.bg.draw(backBuffer);
        
        Collections.sort(this.claimers);

        for(Claimer claimer : this.claimers)
            claimer.draw(backBuffer);

        g.drawImage(backImage, 0, 0, this);
    }
    
    public static void main(String[] args) {

        Main f = new Main("Crowd control test");
        f.setLocationRelativeTo(null);        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JApplet ap = new CrowdTest();
        ap.init();
        ap.start();
        ap.setSize(800, 400);
        f.add("Center", ap);
        f.pack();
        f.setVisible(true);
    }

    private class TimeLine implements Runnable {

        private CrowdTest applet;

        public TimeLine(CrowdTest applet) {
            super();
            this.applet = applet;
        }

        public void run() {
            
            while(true) {
                
                for(Claimer claimer: this.applet.claimers)
                    claimer.step();

                try {                    
                    Thread.sleep(200);
                    this.applet.repaint();
                }
                catch (InterruptedException ex) {
                    Logger.getLogger(CrowdTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class DefaultKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

            if(e.getKeyChar()=='n') {

                Claimer newClaimer = new Claimer();
                try {
                    newClaimer.init();

                    if(claimers.size()<1000)
                        claimers.add(newClaimer);
                    else
                        System.out.println("Hit the maximum claimer number.");
                }
                catch (IOException ex) {
                    Logger.getLogger(CrowdTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if(e.getKeyChar()=='c') {
                claimers.removeAll(claimers);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    }
}
