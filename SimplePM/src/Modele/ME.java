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
    protected Action actionAFaire;
    protected int tempsEntreActions = 175;
    protected Grille grille;

    public ME() {
        actif = true;
    }

    public void start() {
        new Thread(this).start();
    }

    public void action (){         
        grille.deplacement (this);
    }
    
    public void setActionAFaire (Action action){
        this.actionAFaire = action;
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
         try{
            wait();
        }catch(InterruptedException IE){
            IE.printStackTrace();   
        }
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
    public Action getActionAFaire() {
        return this.actionAFaire;
    }
    public void setActif (){
        actif = false;
    }
    
}
