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
public class Grille extends Observable{
    final private HashMap<ME, Point> grilleDynamique;
    final private HashMap<Point,MS> grilleStatique;
    
    public Grille (){
        CreationTerrain creationTerrain = new CreationTerrain ();
        grilleStatique= creationTerrain.getHashMap();
        PacMan pacman = new PacMan ();
       
        
        Fantome fantomeRouge=new Fantome("rouge");
        Fantome fantomeBleu=new Fantome("bleu");
        Fantome fantomeVert=new Fantome("vert");
        
        
        grilleDynamique=new HashMap<ME,Point>();
        grilleDynamique.put (pacman,new Point(1,9));
        grilleDynamique.put (fantomeRouge,new Point (10,9));
        grilleDynamique.put (fantomeBleu,new Point (9,9));
        pacman.setGrille(this);
        fantomeRouge.setGrille (this);
 
        
    }
    
    public void debutJeu() {
        for (ME me : grilleDynamique.keySet()){
            if (me instanceof PacMan)    
                me.start();
         }
        boucleJeu ();
    }
    
    public void boucleJeu() {
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
     
    public void deplacementBas (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        Point inter = new Point (point.x,point.y);
        inter.y++;
        
        if (deplacementPossible(inter)){
            grilleDynamique.replace(entiteDynamique, inter);
        }else{
            if (entiteDynamique instanceof Fantome){
                ((Fantome)entiteDynamique).deplacementAleatoire();
            }
        }
    }
    
    public void deplacementHaut (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        Point inter = new Point (point.x,point.y);
        inter.y--;
                
        if (deplacementPossible(inter)){
            grilleDynamique.replace(entiteDynamique, inter);
        }
        else{
            if (entiteDynamique instanceof Fantome){
                ((Fantome)entiteDynamique).deplacementAleatoire();
            }
        }
    }
    
    public void deplacementDroite (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        Point inter = new Point (point.x,point.y);
        inter.x++;
        
        if (deplacementPossible(inter)){
            grilleDynamique.replace(entiteDynamique, inter);
        }
        else{
            if (entiteDynamique instanceof Fantome){
                ((Fantome)entiteDynamique).deplacementAleatoire();
            }
        }
    }
    
    public void deplacementGauche (ME entiteDynamique){
        Point point = getPoint (entiteDynamique);
        Point inter = new Point (point.x,point.y);
        inter.x--;
        
        if (deplacementPossible(inter)){
            grilleDynamique.replace(entiteDynamique, inter);
        }
        else{
            if (entiteDynamique instanceof Fantome){
                ((Fantome)entiteDynamique).deplacementAleatoire();
            }
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
        
        if (point.y ==9 && point.x==20 )
            point.x=0;
        if (point.y == 9 && point.x == -1)
            point.x = 19;
        
        return possible;
    }
    
    public HashMap<ME, Point> getGrilleDynamique (){
        return grilleDynamique;
    }
    
    public PacMan getPacman (){
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof PacMan)
                return (PacMan)me;
            
        }
        return null;
    }
    
    public Fantome getFantomeRouge (){
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof Fantome)
                if (((Fantome)me).getCouleur()=="rouge")
                    return (Fantome)me;
            
        }
        return null;
    }
    
    public Fantome getFantomeBleu (){
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof Fantome)
                if (((Fantome)me).getCouleur()=="bleu")
                    return (Fantome)me;
            
        }
        return null;
    }
    
    public void passeSurNourriture (PacMan pacman){
        Point point = new Point (grilleDynamique.get(pacman));
        
        if (grilleStatique.get(point) instanceof Couloir)
            ((Mangeable)grilleStatique.get(point)).estMange();
    }
}