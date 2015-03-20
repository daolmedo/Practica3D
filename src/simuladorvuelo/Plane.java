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
public class Plane extends AirportObjects
{
    private float rotation;
    
    public Plane(float x, float y, float z, float rotation)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }
    
    
    public void render()
    {
        System.out.println("Dibujo Avion coord: " + x +", "+ y +", "+ z);
    }
}
