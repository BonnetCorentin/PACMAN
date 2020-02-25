/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;

/**
 *
 * @author coren
 */
public class Grille extends Observable{
    HashMap<ME, Point> grilleDynamique;
    HashMap<Point,MS> grilleStatique;
    
    public Grille (){
        
    }
    
    
}
