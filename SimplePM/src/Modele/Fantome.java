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
public class Fantome extends ME{
    static private Boolean mangeable = false;
    static private int tempsMangeable=150;
    private String couleur;
           
    public Fantome (String couleur,int tempsAvantApparition){
        super (tempsAvantApparition);
        this.couleur = couleur;
        this.tempsAvantApparition=tempsAvantApparition;
        actionEnCour = Action.Haut;
        actionAFaire = Action.Haut;
    }
    
    public String getCouleur (){
        return this.couleur;
    }
    
    static public void setMangeable (){
        mangeable = true;
    }
    
    static public void setNonMangeable (){
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
