/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author
 */
public class CreationTerrain {
   private String nomFichier;
   private BufferedReader bufferLecture;
   
   public CreationTerrain (String nomFichier) {
	this.nomFichier = nomFichier;
        initialisationMap (); 
   }
   
   private char [][]tab;
   private int tailleX;
   private int tailleY;
   private int nbBean;
   
   public void initialisationMap(){
       debutTraitement ();
       String line;
           
        try {
            int i=0;
            while ((line = bufferLecture.readLine()) != null) {
                if (line.length()<10){                   
                    String [] a = line.split("[ ]");
                    tailleX = Integer.parseInt(a[0]);
                    tailleY = Integer.parseInt(a[1]);
                    nbBean = Integer.parseInt(a[2]);
                    tab = new char [tailleY][tailleX];
                }else{
                    String [] a = line.split("[ ]");
               
                    for (int j=0;j<tailleX;j++){
                        tab[i][j]=a[j].charAt(0);
                    }   
                    i++;
                }    
            }
	} catch (IOException e) {
		e.printStackTrace();
	}       
        finDeTraitement ();
   }

   
   private void debutTraitement () {
	try {
		bufferLecture = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichier),"UTF-8"));    		    

	}
	catch (IOException e) {
		    e.printStackTrace();
	}
}
	
    private void finDeTraitement () {
	try {
		bufferLecture.close();
	}
	catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    public int getNbBean (){
        return this.nbBean;
    }
    
    public HashMap<Point,MS> getHashMap (){
        HashMap <Point,MS> hashmap = new HashMap<Point,MS> ();
        
        for (int i=0;i<tailleX;i++){
            for (int j=0;j<tailleY;j++){
                
                Point point = new Point(j,i);
                if (tab[i][j]=='m'){
                    hashmap.put(point,new Mur ("mur"));
                }
                else if (tab[i][j]=='v'){
                    hashmap.put(point,new Mur ("mur2"));
                }
                else if (tab[i][j]=='c'){
                    hashmap.put(point,new Couloir ());
                }
                else if (tab[i][j]=='a'){
                    hashmap.put(point,new Mur("coinD"));
                }
                else if (tab[i][j]=='z'){
                    hashmap.put(point,new Mur("coinD2"));
                }
                else if (tab[i][j]=='e'){
                    hashmap.put(point,new Mur ("coinG"));
                }
                else if (tab[i][j]=='r'){
                    hashmap.put(point,new Mur("coinG2"));
                }
                else if (tab[i][j]=='t'){
                    hashmap.put(point,new Mur ("fermeD"));
                }
                else if (tab[i][j]=='y'){
                    hashmap.put(point,new Mur ("fermeG"));
                }
                else if (tab[i][j]=='q'){
                    hashmap.put(point,new Mur ("fermeH"));
                }
                else if (tab[i][j]=='s'){
                    hashmap.put(point,new Mur("fermeB"));
                }
                else if (tab[i][j]=='u'){
                    hashmap.put(point,new Mur("OuvertH"));
                }
                else if (tab[i][j]=='i'){
                    hashmap.put(point,new Mur("OuvertG"));
                }
                else if (tab[i][j]=='o'){
                    hashmap.put(point,new Mur("OuvertD"));
                }
                else if (tab[i][j]=='p'){
                    hashmap.put(point,new Mur("OuvertB"));
                }
                else if (tab[i][j]=='d'){
                    hashmap.put(point,new Mur("PorteFantome"));
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
