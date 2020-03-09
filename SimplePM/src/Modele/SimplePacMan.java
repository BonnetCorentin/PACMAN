/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fred
 */
public class SimplePacMan extends Observable implements Runnable {
    
    private int[][] tab;
    
    int x, y, sizeX, sizeY;
    
    Random r = new Random();
    
    
    public SimplePacMan(int _sizeX, int _sizeY) {
        x = 0; y = 0;
        
        int ran;
        
        sizeX = _sizeX;
        sizeY = _sizeY;
        
        tab = new int [20][20];
        
        for (int i=0;i<20;i++){
            tab [i] = new int [20];
        }       
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    public void initXY() {
        x = 0;
        y = 0;
    }
    public void initTab (){
        //int ran;
        for (int x=0;x<19;x++) {
        	for(int j=0;j<19;j++) {
            	tab[x][j]=10;
        	}
        	tab[x][0]=11;
        	tab[x][19]=11;
        	tab[19][x]=11;
        	tab[0][x]=11;
        	}
    	tab[19][19]=11;
        tab[1][1]=1;
        tab[18][1]=1;
        tab[1][18]=1;
        tab[18][18]=1;
        tab[10][9]=12;
    	}
    
    
    public void modifierTab (){
        //int ran;
        for (int x=0;x<19;x++) {
        tab[10][9]=12;
        }
    }
/*        for (int i=0;i<20;i++){
            for (int j=0;j<20;j++){
                ran=r.nextInt(14);
                //if (ran==1)
                //    tab [i][j] = 0;
                //else
                //    tab [i][j] = 1;
                tab[i][j]=ran;
            }
        }
    }*/
    
    public int getTab (int i,int j){
        return tab [i][j];
    }
    
    @Override
    public void run() {

     	initTab ();
        while(true) {
        modifierTab();
           //System.out.println(x + " - " + y);
           
           setChanged(); 
           notifyObservers(); // notification de l'observer
           
            try {
                Thread.sleep(300); // pause
            } catch (InterruptedException ex) {
                Logger.getLogger(SimplePacMan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
    
}
