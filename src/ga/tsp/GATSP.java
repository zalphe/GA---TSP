/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ga.tsp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.read.biff.BiffException;

/**
 *
 * @author cups
 */
public class GATSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            GA ga = new GA("C:\\Users\\zalphe\\Documents\\NetBeansProjects\\GA - TSP\\TSP.xls");
            ga.loadData();
            ga.makeKromosom();
            ga.ulangPopulasi();
        } catch (IOException ex) {
            Logger.getLogger(GATSP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(GATSP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
