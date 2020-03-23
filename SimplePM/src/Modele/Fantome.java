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
           
    public Fantome (String couleur){
        super ();
        this.couleur = couleur;
        mangeable=false;
        actionEnCour = Action.Haut;
        actionAFaire = Action.Haut;
    }
    
    public String getCouleur (){
        return this.couleur;
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
