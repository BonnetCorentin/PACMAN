/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author coren
 */
public class PacMan extends ME{
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
        else {
            
        }
    }
    
    public void setAction (Action action){
        this.actionEnCour = action;
    }
    
    public void run (){
        
    }
}
