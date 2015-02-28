/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.kk
 */
package simuladorvuelo;

/**
 *
 * @author dani
 */
public class Airport extends AirportObjects
{
    public Airport(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public void render()
    {
        System.out.println("Dibujo Aeropuerto en coord: " + x +", "+ y +", "+ z);
    }

}
