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
    protected Boolean actif = false;
    protected int tempsEntreActions;
    
    abstract public void action ();
    
    public void run (){
        while (actif){
            action();
            setChanged ();
            notifyObservers ();
            //sleep (tempsEntreActions);
        }
        //grille.retirerDeLenvironnement (this);
        setChanged ();
        notifyObservers ();
    }
}
