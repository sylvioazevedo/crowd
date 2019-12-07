/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sylvioazevedo.crowd.elements;

import java.awt.Graphics2D;
import java.io.IOException;

/**
 *
 * @author alves
 */
public interface Element {

    public void init() throws IOException;
    public void draw(Graphics2D plane);
    public void step();
}
