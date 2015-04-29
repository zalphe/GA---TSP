/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ga.tsp;

/**
 *
 * @author cups
 */
public class Kromosom {
    private String[] gen;
    private double fitnessValue, distance;
    
    public Kromosom(int nGen){
        fitnessValue=100;
        gen = new String[nGen];
    }
    public Kromosom(){
        fitnessValue=100;
    }
    
    public void addKota(String []input){
        gen = new String[input.length];
        gen = input;
    }
    public String getGen(int num){
        return gen[num];
    }
    
    public void setGen(int num, String isi){
        gen[num] = isi;
    }
    public String[] getGen(){
        return gen;
    }

    /**
     * @return the fitnessValue
     */
    public double getFitnessValue() {
        return fitnessValue;
    }

    /**
     * @param fitnessValue the fitnessValue to set
     */
    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
