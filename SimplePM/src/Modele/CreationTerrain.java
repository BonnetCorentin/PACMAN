/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author coren
 */
public class CreationTerrain {
    
   char [][]tab=    {{'b','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','b'},
                    {'b','m','c','c','c','c','c','c','c','c','m','c','c','c','c','c','c','c','c','m','b'},
                    {'b','m','c','m','m','c','m','m','m','c','m','c','m','m','m','c','m','m','c','m','b'},
                    {'b','m','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','m','b'},
                    {'b','m','c','m','m','c','m','c','m','m','m','m','m','c','m','c','m','m','c','m','b'},
                    {'b','m','c','c','c','c','m','c','c','c','m','c','c','c','m','c','c','c','c','m','b'},
                    {'b','m','m','m','m','c','m','m','m','b','m','b','m','m','m','c','m','m','m','m','b'},
                    {'b','b','b','b','m','c','m','b','b','b','b','b','b','b','m','c','m','b','b','b','b'},
                    {'m','m','m','m','m','c','m','b','m','m','m','m','m','b','m','c','m','m','m','m','m'},
                    {'b','b','b','b','b','b','b','b','m','f','b','b','m','b','b','b','b','b','b','b','b'},
                    {'m','m','m','m','m','c','m','b','m','m','m','m','m','b','m','c','m','m','m','m','m'},
                    {'b','b','b','b','m','c','m','b','b','b','b','b','b','b','m','c','m','b','b','b','b'},
                    {'b','m','m','m','m','c','m','b','m','m','m','m','m','b','m','c','m','m','m','m','b'},
                    {'b','m','c','c','c','c','c','c','c','c','m','c','c','c','c','c','c','c','c','m','b'},
                    {'b','m','c','m','m','c','m','m','m','c','m','c','m','m','m','c','m','m','c','m','b'},
                    {'b','m','c','c','m','c','c','c','c','c','c','c','c','c','c','c','m','c','c','m','b'},
                    {'b','m','m','c','m','c','m','c','m','m','m','m','m','c','m','c','m','c','m','m','b'},
                    {'b','m','c','c','c','c','m','c','c','c','m','c','c','c','m','c','c','c','c','m','b'},
                    {'b','m','c','m','m','m','m','m','m','c','m','c','m','m','m','m','m','m','c','m','b'},
                    {'b','m','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','m','b'},
                    {'b','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','b'}
                };
    
    public HashMap<Point,MS> getHashmap (){
        HashMap <Point,MS> hashmap = new HashMap<Point,MS> ();
        
        for (int i=0;i<21;i++){
            for (int j=0;j<21;j++){
                
                Point point = new Point(j,i);
                if (tab[i][j]=='m'){
                    hashmap.put(point,new Mur ());
                }
                else if (tab[i][j]=='c'){
                    hashmap.put(point,new Couloir ());
                }
                else {
                    hashmap.put(point,new CouloirVide ());
                }
            }
            //System.out.println ();
        }
        
        return hashmap;
    }
    
    /* public HashMap<ME,Point> getHashmapDyna (){
        HashMap <ME,Point> hashmapDyna = new HashMap<ME,Point>();
        
         for (int i=0;i<21;i++){
            for (int j=0;j<21;j++){
                
                Point point = new Point(j,i);
                if (tab[i][j]=='f'){
                    hashmapDyna.put(new Fantome(),point);
                }
                else if (tab[i][j]=='p'){
                    
                }
                else {
                }
            }
        }
               
        return hashmapDyna;
    }*/
}
