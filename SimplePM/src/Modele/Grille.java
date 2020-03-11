/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author coren
 */
public class Grille extends Observable implements Runnable{
    final private HashMap<ME, Point> grilleDynamique;
    final private HashMap<Point,MS> grilleStatique;
    
    public Grille (){
        CreationTerrain creationTerrain = new CreationTerrain ();
        grilleStatique= creationTerrain.getHashmap();
        PacMan pacman = new PacMan ();
       
        
        Fantome fantomeRouge=new Fantome("rouge");
        Fantome fantomeBleu=new Fantome("bleu");
        Fantome fantomeVert=new Fantome("vert");
        
        
        grilleDynamique=new HashMap<ME,Point>();
        grilleDynamique.put (pacman,new Point(1,9));
        grilleDynamique.put (fantomeRouge,new Point (10,10));
        pacman.setGrille(this);
        fantomeRouge.setGrille (this);
 
        
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        for (ME me : grilleDynamique.keySet()){
               me.start();
               me.run ();
        }
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
    
    public Point[] getPointME(int i){
        return grilleDynamique.keySet().toArray(ME);
    }
    
     
    public void deplacementBas (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        point.y++;
        
        if (deplacementPossible(point)){
            grilleDynamique.replace(entiteDynamique, point);
        }
    }
    
    public void deplacementHaut (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        point.y--;
                
        if (deplacementPossible(point)){
            grilleDynamique.replace(entiteDynamique, point);
        }
    }
    
    public void deplacementDroite (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        point.x++;
        
        if (deplacementPossible(point)){
            grilleDynamique.replace(entiteDynamique, point);
        }
    }
    
    public void deplacementGauche (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        point.x--;
        
        if (deplacementPossible(point)){
            grilleDynamique.replace(entiteDynamique, point);
        }
    }
    
    public void retirerDeLenvironnement (ME me){
        grilleDynamique.remove(me);
    }
    
    private Point getPoint (ME entiteDynamqique){
        Point point = grilleDynamique.get(entiteDynamqique);
        return point;
    }
    
    private Boolean deplacementPossible (Point point){
        Boolean possible = true;
       
        if (grilleStatique.get(point) instanceof Mur){
            possible = false;
        }
        
        return possible;
    }
}