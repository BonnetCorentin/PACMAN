/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import VueControleur.PorteFantome;
import VueControleur.SimpleVC;
import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author
 */
public class CreationTerrain {
    
   char [][]tab=    {{'b','a','v','v','v','v','v','v','v','v','p','v','v','v','v','v','v','v','v','e','b'},
                    {'b','m','c','c','c','c','c','c','c','c','m','c','c','c','c','c','c','c','c','m','b'},
                    {'b','m','c','y','t','c','y','v','t','c','s','c','y','v','t','c','y','t','c','m','b'},
                    {'b','m','f','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','f','m','b'},
                    {'b','m','c','y','t','c','q','c','y','v','p','v','t','c','q','c','y','t','c','m','b'},
                    {'b','m','c','c','c','c','m','c','c','c','m','c','c','c','m','c','c','c','c','m','b'},
                    {'b','r','v','v','e','c','o','v','t','b','s','b','y','v','i','c','a','v','v','z','b'},
                    {'b','b','b','b','m','c','m','b','b','b','b','b','b','b','m','c','m','b','b','b','b'},
                    {'v','v','v','v','z','c','s','b','a','d','d','d','e','b','s','c','r','v','v','v','v'},
                    {'b','b','b','b','b','b','b','b','m','b','b','b','m','b','b','b','b','b','b','b','b'},
                    {'v','v','v','v','e','c','q','b','r','v','v','v','z','b','q','c','a','v','v','v','v'},
                    {'b','b','b','b','m','c','m','b','b','b','b','b','b','b','m','c','m','b','b','b','b'},
                    {'b','a','v','v','z','c','s','b','y','v','p','v','t','b','s','c','r','v','v','e','b'},
                    {'b','m','c','c','c','c','c','c','c','c','m','c','c','c','c','c','c','c','c','m','b'},
                    {'b','m','c','y','e','c','y','v','t','c','s','c','y','v','t','c','a','t','c','m','b'},
                    {'b','m','f','c','m','c','c','c','c','c','c','c','c','c','c','c','m','c','f','m','b'},
                    {'b','o','t','c','s','c','q','c','y','v','p','v','t','c','q','c','s','c','y','i','b'},
                    {'b','m','c','c','c','c','m','c','c','c','m','c','c','c','m','c','c','c','c','m','b'},
                    {'b','m','c','y','v','v','u','v','t','c','s','c','y','v','u','v','v','t','c','m','b'},
                    {'b','m','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','m','b'},
                    {'b','r','v','v','v','v','v','v','v','v','v','v','v','v','v','v','v','v','v','z','b'}
                };
    
    public HashMap<Point,MS> getHashmap (){
        HashMap <Point,MS> hashmap = new HashMap<Point,MS> ();
        //SimpleVC svc = new SimpleVC();
        
        for (int i=0;i<21;i++){
            for (int j=0;j<21;j++){
                
                Point point = new Point(j,i);
                if (tab[i][j]=='m'){
                    hashmap.put(point,new Mur ());
                }
                else if (tab[i][j]=='v'){
                    hashmap.put(point,new Mur2 ());
                }
                else if (tab[i][j]=='c'){
                    hashmap.put(point,new Couloir ());
                }
                else if (tab[i][j]=='a'){
                    hashmap.put(point,new CoinDroite ());
                }
                else if (tab[i][j]=='z'){
                    hashmap.put(point,new CoinD2 ());
                }
                else if (tab[i][j]=='e'){
                    hashmap.put(point,new CoinG ());
                }
                else if (tab[i][j]=='r'){
                    hashmap.put(point,new CoinG2 ());
                }
                else if (tab[i][j]=='t'){
                    hashmap.put(point,new FermeD ());
                }
                else if (tab[i][j]=='y'){
                    hashmap.put(point,new FermeG ());
                }
                else if (tab[i][j]=='q'){
                    hashmap.put(point,new FermeH ());
                }
                else if (tab[i][j]=='s'){
                    hashmap.put(point,new FermeB ());
                }
                else if (tab[i][j]=='u'){
                    hashmap.put(point,new OuvertHaut ());
                }
                else if (tab[i][j]=='i'){
                    hashmap.put(point,new OuvertGauche ());
                }
                else if (tab[i][j]=='o'){
                    hashmap.put(point,new OuvertDroite ());
                }
                else if (tab[i][j]=='p'){
                    hashmap.put(point,new OuvertBas ());
                }
                else if (tab[i][j]=='d'){
                    hashmap.put(point,new PorteFantome ());
                }
                else if (tab[i][j]=='f'){
                    hashmap.put(point,new GrosBean ());
                }
                else {
                    hashmap.put(point,new CouloirVide ());
                }
            }
        
        }
        return hashmap;
    }

}
