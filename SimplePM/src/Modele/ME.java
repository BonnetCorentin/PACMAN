/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Observable;

/**
 *
 * @author coren
 */
abstract public class ME extends Observable implements Runnable{
    Boolean actif;
    protected Action actionEnCour;
    protected int tempsEntreActions=300;
    protected Grille grille;
   
    public ME (){
        actionEnCour = Action.Droite;
        actif = true;
    }
    
    
    public void start() {
        new Thread(this).start();
    }
    
    abstract public void action ();
    
    public void run (){
        while (actif){
            action();
            setChanged ();
            notifyObservers ();
            
            try {
                Thread.sleep (tempsEntreActions);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        grille.retirerDeLenvironnement (this);
        setChanged ();
        notifyObservers ();
    }
    
    abstract public void setAction (Action action);
    
    public void setGrille (Grille grille){
        this.grille = grille;
    }
    
    public Action getAction (){
        return this.actionEnCour;
    }
}
