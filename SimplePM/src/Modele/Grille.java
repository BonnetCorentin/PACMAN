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
        CreationTerrain creationTerrain = new CreationTerrain ();
        grilleStatique= creationTerrain.getHashmap();
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        while(true) {
                   
           setChanged(); 
           notifyObservers(); // notification de l'observer
           
            try {
                Thread.sleep(300); // pause
            } catch (InterruptedException ex) {
                Logger.getLogger(SimplePacMan.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    
    }

    public Point getvalueGD(Point p){
        return this.grilleDynamique.get(p);
    }

    public MS getvalueGS(Point p){
        return this.grilleStatique.get(p);
    }
}