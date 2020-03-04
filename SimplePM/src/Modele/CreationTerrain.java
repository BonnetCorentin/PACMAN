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
    
   char [][]tab={{'c','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','m','c'},
            {'c','m','c','c','c','c','c','c','c','c','m','c','c','c','c','c','c','c','c','m','c'},
            {'c','m','c','m','m','c','m','m','m','c','m','c','m','m','m','c','m','m','c','m','c'},
            {'c','m','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','m','c'},
            {'c','m','c','m','m','c','m','c','m','m','m','m','m','c','m','c','m','m','c','m','c'},
            {'c','m','c','c','c','c','m','c','c','c','m','c','c','c','m','c','c','c','c','m','c'},
            {'c','m','m','m','m','c','m','m','m','c','m','c','m','m','m','c','m','m','m','m','c'},
            {'c','c','c','c','m','c','m','c','c','c','c','c','c','c','m','c','m','c','c','c','c'},
            {'m','m','m','m','m','c','m','c','m','m','m','m','m','c','m','c','m','m','m','m','m'},
            {'c','c','c','c','c','c','c','c','m','c','c','c','m','c','c','c','c','c','c','c','c'},
            {'m','m','m','m','m','c','m','c','m','m','m','m','m','c','m','c','m','m','m','m','m'},
            {'c','c','c','c','m','c','m','c','c','c','c','c','c','c','m','c','m','c','c','c','c'},
            {'c','m','m','m','m','c','m','c','m','m','m','m','m','c','m','c','m','m','m','m','c'},
            {'c','m','c','c','c','c','c','c','c','c','m','c','c','c','c','c','c','c','c','m','c'}
        };
    
    public HashMap<Point,MS> getHashmap (){
        HashMap <Point,MS> hashmap = new HashMap<Point,MS> ();
        
        for (int i=0;i<14;i++){
            for (int j=0;j<21;j++){
                //System.out.print(tab[i][j]);
                Point point = new Point(j,i);
                if (tab[i][j]=='m'){
                    hashmap.put(point,new Mur ());
                }
                else {
                    hashmap.put(point,new Couloir ());
                }
                    
            }
            //System.out.println ();
        }
        
        return hashmap;
    }
    
}
