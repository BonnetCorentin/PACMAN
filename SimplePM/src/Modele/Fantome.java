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
    static private Boolean mangeable = false;
    static private int tempsMangeable=50;
    private String couleur;
           
    public Fantome (String couleur){
        super ();
        this.couleur = couleur;
        actionEnCour = Action.Haut;
        actionAFaire = Action.Haut;
    }
    
    public String getCouleur (){
        return this.couleur;
    }
    
    static public void setMangeable (){
        mangeable = true;
    }
    
    static private void setNonMangeable (){
        mangeable = false;
    }
    
    static public Boolean estMangeable (){
        return mangeable;
    }
    
    static public int getTempsMangeable (){
        return tempsMangeable;
    }
    
    static public void setTempsMangeable (){
        tempsMangeable=100;
    }
    
    static public void decrementerTempsMangeable (){
        tempsMangeable--;
        if (tempsMangeable <0){
            tempsMangeable = 50;
            mangeable = false;
        }
    }
}
