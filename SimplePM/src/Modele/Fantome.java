/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Random;

/**
 *
 * @author coren
 */
public class Fantome extends ME{
    private Boolean mangeable;
    private String couleur;
       
     public synchronized void action (){         
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
    
    public Fantome (String couleur){
        this.couleur = couleur;
        mangeable=false;
        actionEnCour = Action.Haut;
    }
    
    public String getCouleur (){
        return this.couleur;
    }
    
    public void setAction (Action action){
        this.actionEnCour = action;
    }
    
    public void deplacementAleatoire (){
        Random rand = new Random ();
        int a = rand.nextInt(4);
        
        switch (a){
            case 0:
                actionEnCour = Action.Haut;
            break;
            case 1:
                actionEnCour = Action.Gauche;
            break;
            case 2:
                actionEnCour = Action.Bas;
            break;
            case 3:
                actionEnCour = Action.Droite;
            break;
            default:
                System.out.println ("a");
            break;
        }
    }
}
