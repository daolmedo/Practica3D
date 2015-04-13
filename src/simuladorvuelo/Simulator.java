/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simuladorvuelo;

import java.util.ArrayList;

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
