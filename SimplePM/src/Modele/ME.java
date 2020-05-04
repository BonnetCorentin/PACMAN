/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import static java.lang.Thread.sleep;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author coren
 */
abstract public class ME extends Observable implements Runnable {
    protected Action actionEnCour;
    protected Action actionAFaire;
    protected int tempsEntreActions = 200;
    protected Grille grille;
    protected int tempsAvantApparition;
    protected Boolean actif;
    static Boolean actionPossible = false;

    public ME(int tempsAvantApparition) {
        this.tempsAvantApparition=tempsAvantApparition;
        actif = false;
    }

    public void start() {
        new Thread(this).start();
    }
    
    public static void setActionPossible (){
        actionPossible=true;
    }
    
     public static void setActionImpossible (){
        actionPossible=false;
    }

    public void action() {
        synchronized (grille) {
            grille.deplacement(this);
        }
    }

    public void setActionAFaire(Action action) {
        this.actionAFaire = action;
    }

    public void setAction(Action action) {
        this.actionEnCour = action;
    }
    
    public void threadSleep (){
        try {
            sleep(getTempsAvantApparition());
            setActif ();
        } catch (InterruptedException ex) {
            Logger.getLogger(Grille.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @Override
    public void run() {
        threadSleep ();
        while (keepGoing()) {
            if (actionPossible){
                action();
                if (Fantome.estMangeable()){
                    synchronized(grille){
                       grille.mangerFantome();
                       Fantome.decrementerTempsMangeable ();
                    }
                }else {
                    synchronized (grille){
                        String couleur=new String ();
                        if (grille.pacmanMort()) {
                            setActionImpossible();
                            grille.getGestionStat().setVie();
                            grille.remettrePacMandebut();
                        }
                    } 
                }
                
                synchronized(grille){
                    grille.passeSurNourriture();
                }
                setChanged();
                notifyObservers();

                try {
                    Thread.sleep(tempsEntreActions);
                } catch (InterruptedException ex) {

                }
            }
            else {
                System.out.println("Veuillez appuier sur une touche pour commencer.");
            }
            
        }
        retirerEnvironnement();
        setChanged();
        notifyObservers();
    }

    protected void retirerEnvironnement() {
        grille.retirerDeLenvironnement(this);
    }

    protected Boolean keepGoing() {
        return actif;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    public Action getAction() {
        return this.actionEnCour;
    }

    public Action getActionAFaire() {
        return this.actionAFaire;
    }

    public void setActif() {
        this.actif = true;
    }   
    
    public void stopJeu (){
        this.actif = false;
    }
    
    public int getTempsAvantApparition (){
        return this.tempsAvantApparition;
    }

}
