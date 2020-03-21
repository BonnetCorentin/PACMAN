/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Observable;

/**
 *
 * @author coren
 */
abstract public class ME extends Observable implements Runnable {

    Boolean actif;
    protected Action actionEnCour;
    protected int tempsEntreActions = 200;
    protected Grille grille;

    public ME() {
        actionEnCour = Action.Droite;
        actif = true;
    }

    public void start() {
        new Thread(this).start();
    }

    public void action (){         
        if (actionEnCour == Action.Bas){
            grille.deplacementBas(this);
        }
        else if (actionEnCour == Action.Haut){
            grille.deplacementHaut(this);
        }
        else if (actionEnCour == Action.Gauche){
            grille.deplacementGauche(this);
        }
        else if (actionEnCour == Action.Droite){
            grille.deplacementDroite(this);
        }
    }
    
    public void setAction (Action action){
        this.actionEnCour = action;
    }

    @Override
    public void run() {
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        Runnable processDataCmd = new Runnable() {
            @Override
            public void run() {
                action();
                setChanged();
                notifyObservers();

                if (!keepGoing()) {
                    retirerEnvironnement();
                    setChanged();
                    notifyObservers();
                }

            }
        };
        service.scheduleAtFixedRate(processDataCmd, 0, tempsEntreActions, TimeUnit.MILLISECONDS);
    }

    private void retirerEnvironnement() {
        grille.retirerDeLenvironnement(this);
    }

    private Boolean keepGoing() {
        return actif;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    public Action getAction() {
        return this.actionEnCour;
    }
}
