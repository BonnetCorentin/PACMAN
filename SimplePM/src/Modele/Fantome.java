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
}
