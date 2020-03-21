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
public class GestionStat {
    private int vie;
    private int score;
    
    public GestionStat (){
        vie = 3;
        score = 0;
    }
    
    public void augmenterScore (int n){
        score +=n;
    }
    
    public int getScore (){
        return this.score;
    }
    
}
