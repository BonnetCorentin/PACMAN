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
    private Boolean mangeable;
    private String couleur;
       
    public void action (){
        
    }
    
    public Fantome (String couleur){
        this.couleur = couleur;
        mangeable=false;
    }
    
    public void run (){
        
    }
    
    public void setAction (Action action){
        this.actionEnCour = action;
    }
}
