/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorvuelo;

/**
 *
 * @author dani
 */


import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;

public class Plane extends AirportObjects
{
    private float rotation;
    
    public Plane(float x, float y, float z, float rotation)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;

        //declara los datos
        FloatBuffer vertices = BufferUtils.createFloatBuffer(3 * 6);
            vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);
            vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
            vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
            vertices.flip(); 
        //Crea un buffer

        int vbo = glGenBuffers();
        //Conecto al buffer
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //Mete los datos declarados en java en el buffer
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        
    }
    
    
    public void render()
    {
        Draw.drawplanes();
    }
}
