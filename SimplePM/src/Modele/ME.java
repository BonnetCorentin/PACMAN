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
abstract public class ME extends Observable implements Runnable {
    protected Action actionEnCour;
    protected Action actionAFaire;
    protected int tempsEntreActions = 200;
    protected Grille grille;
    protected int tempsAvantApparition;
    protected Boolean actif;
    private static boolean x=false;
    static Boolean actionPossible = false;

    
    public static boolean getX() {
		return x;
	}

	public static void setX(boolean x) {
		ME.x = x;
	}


	public Action getActionEnCour() {
		return actionEnCour;
	}

	public int getTempsEntreActions() {
		return tempsEntreActions;
	}

	public Grille getGrille() {
		return grille;
	}

	public Boolean getActif() {
		return actif;
	}

	public static Boolean getActionPossible() {
		return actionPossible;
	}

	public ME(int tempsAvantApparition) {
        this.tempsAvantApparition=tempsAvantApparition;
        actif = true;
    }
        
    public Boolean peutBouger (){
        if (tempsAvantApparition<0)
            return true;
        else{
            tempsAvantApparition --;
            return false;
        }
    } 

    
    public void setTempAvantApparition (int i){
        tempsAvantApparition=i;
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
   
    @Override
    public void run() {
        while (keepGoing()) {
            if (actionPossible && peutBouger ()){
                action();
                if (Fantome.estMangeable()){
                    synchronized(grille){
                       grille.mangerFantome();
                    }
                }else {
                    synchronized (grille){
                        if (grille.pacmanMort()) {
                            setActionImpossible();
                            grille.getGestionStat().setVie();
                            grille.remettrePacMandebut();
                            x = true;
                        }
                    } 
                }
                
                synchronized(grille){
                    grille.passeSurNourriture();
                }
                setChanged();
                notifyObservers();
            }
     
            try {
                Thread.sleep(tempsEntreActions);
            } catch (InterruptedException ex) {

            }
            
        }
        setChanged();
        notifyObservers();
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
    
    public int getTempsAvantApparition (){
        return this.tempsAvantApparition;
    }

}
