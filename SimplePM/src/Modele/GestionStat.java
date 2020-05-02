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
    private int nbBin;

    public GestionStat() {
        vie = 3;
        score = 0;
        nbBin = 20;
    }

    public void augmenterScore(int n) {
        score += n;
    }

    public int getScore() {
        return this.score;
    }

    public int getVie() {
        return vie;
    }

    public void setVie() {
        //if (e instanceof PacMan) {
            if (vie > 0) {
                vie = vie - 1;
            }
        //}
    }
    public void diminuerBin (){
        nbBin--;
    }

}
