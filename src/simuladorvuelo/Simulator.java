/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simuladorvuelo;

import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *
 * @author dani
 */
public class Simulator {
    
    private static ArrayList<AirportObjects> objects;
    private int planes;
    

    
    public Simulator()
    {
        objects = new ArrayList<AirportObjects>();
        
        Airport barajas = new Airport(1,2,3);
        objects.add(barajas);
        planes = 4;
        for(int i = 0; i < planes; i++)
        {
            Plane plane = new Plane(1,2,3,0); 
            objects.add(plane);
        }
    }
    
    public void render()
    {
        for (AirportObjects ao: objects)
            {
                ao.render();
            } 
    }
}
