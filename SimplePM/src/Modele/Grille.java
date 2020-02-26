/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author coren
 */
public class Grille extends Observable implements Runnable{
    private HashMap<ME, Point> grilleDynamique;
    private HashMap<Point,MS> grilleStatique;
    
    public Grille (){
        
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    private void initialisationGrille (){
        for (int i=0;i<19;i++){
            grilleStatique.put(new Point(i,0), new Mur ());
        }
        
        for (int i=1;i<9;i++){
            grilleStatique.put(new Point(1,i), new Couloir ());
        }
        
        for (int i=0;i<7;i++){
            grilleStatique.put(new Point(0,i), new Mur ());
        }
    }
    
    @Override
    public void run() {
        while(true) {
        	
           //System.out.println(x + " - " + y);
           
           setChanged(); 
           notifyObservers(); // notification de l'observer
           
            try {
                Thread.sleep(300); // pause
            } catch (InterruptedException ex) {
                Logger.getLogger(SimplePacMan.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    
    }
    
    
}
