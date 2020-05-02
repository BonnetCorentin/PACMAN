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

    Boolean actif;
    protected Action actionEnCour;
    protected Action actionAFaire;
    protected int tempsEntreActions = 200;
    protected Grille grille;

    public ME() {
        actif = true;
    }

    public void start() {
        new Thread(this).start();
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
                    grille.getGestionStat().setVie();
                    grille.remettrePacMandebut();
                    }
                } 
            }

            grille.passeSurNourriture();

            setChanged();
            notifyObservers();

            try {
                Thread.sleep(tempsEntreActions);
            } catch (InterruptedException ex) {

            }
        }
        retirerEnvironnement();
        setChanged();
        notifyObservers();
    }

    protected void retirerEnvironnement() {
        try {
            wait();
        } catch (InterruptedException IE) {
            IE.printStackTrace();
        }
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
        actif = false;
    }

}
