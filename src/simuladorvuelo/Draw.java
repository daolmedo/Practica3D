/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorvuelo;


import static org.lwjgl.opengl.GL11.*;


/**
 * @author dani
 */
public class Draw {
    
    public static void drawplanes() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

    /* Render triangle */
    glDrawArrays(GL_TRIANGLES, 0, 3);


    }
}