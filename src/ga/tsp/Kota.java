/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ga.tsp;

import static java.lang.Math.sqrt;

/**
 *
 * @author cups
 */
public class Kota {
    private String id,x,y;
    private double ed;

    public Kota(){}
    
    public Kota(String id,String x,String y){
        this.x = x;
        this.y = y;
        this.id = id;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public String getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     * @return the ed
     */
    public double getEd() {
        return ed;
    }

    /**
     * @param ed the ed to set
     */
    public void setEd(double ed) {
        this.ed = ed;
    }
    
    
}
