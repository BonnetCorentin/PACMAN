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
public class Mur extends NomMangeable{
    private String typeMur;
    
    public Mur (String typeMur){
        this.typeMur=typeMur;
    }
    
    public String getTypeMur (){
        return this.typeMur;
    }
    
    
}
